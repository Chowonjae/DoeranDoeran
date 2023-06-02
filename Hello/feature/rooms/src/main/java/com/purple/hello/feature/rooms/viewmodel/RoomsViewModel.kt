package com.purple.hello.feature.rooms.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.*
import com.purple.hello.domain.rooms.*
import com.purple.hello.feature.rooms.navigation.roomIdArg
import com.purple.hello.feature.rooms.state.RoomsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRoomListFlow: GetRoomListUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val getJoinInfoUseCase: GetJoinInfoUseCase,
    private val fetchRoomsUseCase: FetchRoomsUseCase,
    private val createRoomUseCase: CreateRoomUseCase,
    private val fetchRoomDetail: FetchRoomDetailUseCase,
    private val getRoomCode: GetRoomCodeUseCase,
) : ViewModel() {

    private val rooms: Flow<Result<List<Room>>> = getRoomListFlow().asResult()

    private val selectedRoomId = savedStateHandle.getStateFlow(roomIdArg, 0L)
    val roomCode: MutableStateFlow<String> = MutableStateFlow("")

    val roomsUiState: StateFlow<RoomsUiState> =
        rooms.map {
            when (it) {
                is Result.Success -> { RoomsUiState.Success(it.data) }
                is Result.Loading -> { RoomsUiState.Loading }
                is Result.Error -> { RoomsUiState.Error(it.exception) }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RoomsUiState.Loading,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedRoom: StateFlow<Room?> = selectedRoomId.flatMapLatest {
        rooms.map {
            when (it) {
                is Result.Success -> {
                    it.data.filter { it.roomId == selectedRoomId.value }.let { selectedRoom ->
                        if (selectedRoom.isEmpty()) {
                            null
                        } else {
                            selectedRoom.first()
                        }
                    }
                }
                else -> null
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    suspend fun createRoom(
        roomName: String,
        userName: String,
        question: String,
        password: String,
        onRoomCreate: (Long) -> Unit,
    ) {
        val roomId = createRoomUseCase(roomName, userName, question, password)
        onRoomCreate(roomId)
    }

    suspend fun joinRoom(roomId: Long, joinRoomData: JoinRoomInputValue) = joinRoomUseCase(roomId, joinRoomData)

    suspend fun fetchRoom() = fetchRoomsUseCase()

    fun fetchRoomDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRoomDetail(selectedRoomId.value)
            roomCode.emit(getRoomCode(selectedRoomId.value))
        }
    }

    suspend fun getJoinData(joinRoomId: Long) = getJoinInfoUseCase(joinRoomId)
}
