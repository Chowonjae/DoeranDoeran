package com.purple.data.rooms.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RoomNameUpdateRequest(
    val userRoomId: Long,
    val roomName: String,
)
