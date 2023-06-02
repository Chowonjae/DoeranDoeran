package com.purple.hello.feature.rooms.state

import com.purple.core.model.Room

sealed interface RoomDetailUiState {
    object Loading : RoomDetailUiState

    data class Success(
        val roomDetail: Room,
    ) : RoomDetailUiState

    data class Error(val message: Throwable?) : RoomDetailUiState
}
