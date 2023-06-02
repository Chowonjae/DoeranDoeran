package com.purple.data.rooms.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomPasswordQuestionResponse(
    @SerialName("roomId")
    val roomId: Long,
    @SerialName("roomQuestion")
    val roomQuestion: String,
)
