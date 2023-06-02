package com.purple.data.rooms.model.response

import com.purple.core.database.entity.RoomEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomListResponse(
    @SerialName("members")
    val members: List<MemberResponse>,
    @SerialName("roomId")
    val roomId: Long,
    @SerialName("roomName")
    val roomName: String,
    @SerialName("userRoomId")
    val userRoomId: Long,
)

fun RoomListResponse.asRoomEntity() = RoomEntity(
    roomId,
    userRoomId,
    roomName,
    recentVisitedTime = 0L,
)
