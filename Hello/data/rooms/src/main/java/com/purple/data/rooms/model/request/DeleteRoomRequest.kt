package com.purple.data.rooms.model.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteRoomRequest(
    val roomId: Long,
)
