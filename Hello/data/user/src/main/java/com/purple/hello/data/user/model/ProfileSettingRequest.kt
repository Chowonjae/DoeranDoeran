package com.purple.hello.data.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileSettingRequest(
    @SerialName("profileURL") val profileUrl: String?,
    val birth: String,
)
