package com.purple.data.rooms.model.response

import kotlinx.serialization.Serializable

@Serializable
data class QuestionResponse(
    val questionId: Long,
    val content: String,
)
