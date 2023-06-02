package com.purple.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "user_room_cross",
    primaryKeys = ["userId", "roomId"],
    indices = [Index("roomId")],
    foreignKeys = [
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = CASCADE,
        ),
    ],
)
data class MemberRoomEntity(
    val userId: Long,
    val roomId: Long,
    val nickName: String,
    val role: String,
)
