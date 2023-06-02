package com.purple.hello.feature.rooms.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.Feed
import com.purple.core.model.Result
import com.purple.core.model.asResult
import com.purple.hello.domain.account.GetUserIdUseCase
import com.purple.hello.domain.rooms.*
import com.purple.hello.domain.rooms.feed.FetchDateFeedUseCase
import com.purple.hello.domain.rooms.feed.GetDateFeedUseCase
import com.purple.hello.domain.rooms.feed.GetQuestionUseCase
import com.purple.hello.feature.rooms.navigation.roomIdArg
import com.purple.hello.feature.rooms.state.FeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FeedViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getQuestionFlow: GetQuestionUseCase,
    getFeedFlow: GetDateFeedUseCase,
    getUserIdUseCase: GetUserIdUseCase,
    private val fetchDateFeed: FetchDateFeedUseCase,
) : ViewModel() {

    private var selectedRoomId: Long = checkNotNull(savedStateHandle[roomIdArg])
    private val selectedDate = MutableSharedFlow<LocalDateTime>()

    private val dateQuestion: Flow<Result<String?>> = selectedDate.flatMapLatest {
        getQuestionFlow(roomId = selectedRoomId, date = it).asResult()
    }
    private val dateFeedList: Flow<Result<List<Feed>>> = selectedDate.flatMapLatest {
        getFeedFlow(roomId = selectedRoomId, date = it).asResult()
    }

    val feedUiState: StateFlow<FeedUiState> =
        combine(
            dateFeedList,
            dateQuestion,
        ) { feedList, question ->
            when (feedList) {
                is Result.Loading -> FeedUiState.Loading
                is Result.Success -> {
                    val userId = runBlocking { getUserIdUseCase().first() }
                    FeedUiState.Success(
                        feeds = feedList.data,
                        isPossibleToUpload = feedList.data.none { feed ->
                            feed.author.id == userId
                        },
                        question = when(question) {
                            is Result.Error -> ""
                            is Result.Loading -> ""
                            is Result.Success -> question.data
                        }
                    )
                }
                is Result.Error -> FeedUiState.Error(feedList.exception)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FeedUiState.Loading,
        )

    fun selectDate(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedDate.emit(date)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchFeed(date: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDateFeed(
                date = date,
                roomId = selectedRoomId,
            )
        }
    }
}
