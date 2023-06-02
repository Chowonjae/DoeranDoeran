package com.purple.data.rooms.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RoomJoinRequest(
    val roomId: Long,
    val roomName: String,
    val roomPassword: String,
    val userName: String,
)
