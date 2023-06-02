package com.purple.data.rooms.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomJoinInfoResponse (
    @SerialName("roomId")
    val roomId: Long,
    @SerialName("roomName")
    val roomName: String,
    @SerialName("roomQuestion")
    val roomQuestion: String,
)
