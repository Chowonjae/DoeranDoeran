package com.purple.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "rooms",
    primaryKeys = ["roomId"],
)
data class RoomEntity(
    val roomId: Long,
    val userRoomId: Long,
    val roomName: String,
    val recentVisitedTime: Long,
)
