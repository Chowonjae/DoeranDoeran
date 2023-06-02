package com.purple.core.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Member(
    val id: Long,
    val nickName: String,
    val profileUrl: String?,
    val birth: LocalDate? = null,
)

data class Profile(
    val profileUrl: String?,
    val birth: LocalDateTime?,
)
