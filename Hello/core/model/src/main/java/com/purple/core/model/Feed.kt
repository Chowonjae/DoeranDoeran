package com.purple.core.model

data class Feed(
    val feedId: Long,
    val headerImageUrl: String?,
    val content: String?,
    val author: Member,
)
