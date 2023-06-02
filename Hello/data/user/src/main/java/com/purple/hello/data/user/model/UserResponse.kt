package com.purple.hello.data.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val birth: String?,
    val userId: Long,
    @SerialName("profileURL") val profileUrl: String?,
)

@Serializable
data class ProfileSetResponse(
    @SerialName("birth") val birth: String?,
    @SerialName("profileURL") val profileUrl: String?,
)
