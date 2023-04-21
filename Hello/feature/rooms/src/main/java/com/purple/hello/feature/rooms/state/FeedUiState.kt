package com.purple.hello.feature.rooms.state

import com.purple.hello.domain.feature.model.Feed
import java.util.*

sealed interface FeedUiState {
    object Loading : FeedUiState

    data class Loaded(
        val date: Date,
        val feeds: List<Feed>,
        val question: String,
    ) : FeedUiState

    data class Error(val message: String?) : FeedUiState
}
