package com.purple.hello.feature.rooms.state

import com.purple.core.model.Feed

sealed interface FeedUiState {
    object Loading : FeedUiState

    data class Success(
        val feeds: List<Feed>,
        val question: String?,
        val isPossibleToUpload: Boolean,
    ) : FeedUiState

    data class Error(val message: Throwable?) : FeedUiState
}
