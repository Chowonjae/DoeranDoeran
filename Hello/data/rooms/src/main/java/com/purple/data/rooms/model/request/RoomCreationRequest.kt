package com.purple.data.rooms.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RoomCreationRequest(
    val roomName: String,
    val userName: String,
    val roomQuestion: String,
    val roomPassword: String,
)
