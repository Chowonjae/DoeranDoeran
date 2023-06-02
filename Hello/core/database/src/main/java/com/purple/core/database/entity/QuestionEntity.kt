package com.purple.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "question",
    primaryKeys = ["questionId"],
)
data class QuestionEntity(
    val questionId: Long,
    val content: String,
)
