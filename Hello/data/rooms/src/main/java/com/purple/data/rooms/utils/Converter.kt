package com.purple.data.rooms.utils

import com.purple.core.database.entity.RoomEntity
import com.purple.data.rooms.model.response.RoomCreationResponse

fun RoomCreationResponse.asRoomEntity() = RoomEntity(
    roomId = this.roomId,
    userRoomId = this.userRoomId,
    roomName = this.roomName,
    recentVisitedTime = System.currentTimeMillis(),
)
