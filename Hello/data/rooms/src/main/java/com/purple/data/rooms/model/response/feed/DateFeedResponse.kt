package com.purple.data.rooms.model.response.feed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateFeedResponse(
    @SerialName("content") val content: String?,
    @SerialName("createAt") val createdAt: String,
    @SerialName("feedUrl") val feedUrl: String?,
    @SerialName("feedId") val feedId: Long,
    @SerialName("userId") val userId: Long,
)
