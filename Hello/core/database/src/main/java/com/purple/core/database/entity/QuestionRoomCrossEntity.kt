package com.purple.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import java.time.LocalDate

@Entity(
    tableName = "room_question_cross",
    primaryKeys = ["date", "roomId"],
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["questionId"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.NO_ACTION,
        ),
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class QuestionRoomCrossEntity(
    val questionId: Long,
    val date: LocalDate,
    val roomId: Long,
)
