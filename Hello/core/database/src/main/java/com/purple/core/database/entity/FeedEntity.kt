package com.purple.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import java.time.LocalDateTime

@Entity(
    tableName = "feed",
    primaryKeys = ["feedId"],
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.NO_ACTION,
        ),
    ],
)
data class FeedEntity(
    val feedId: Long,
    val roomId: Long,
    val userId: Long,
    val createAt: LocalDateTime,
    val feedUrl: String?,
    val content: String?,
)
