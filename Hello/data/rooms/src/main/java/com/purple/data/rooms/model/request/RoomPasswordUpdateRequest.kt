package com.purple.data.rooms.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RoomPasswordUpdateRequest(
    val roomId: Long,
    val roomPassword: String,
    val roomQuestion: String,
)
