package com.purple.data.rooms.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UserNameUpdateRequest(
    val userRoomId: Long,
    val userName: String,
)
