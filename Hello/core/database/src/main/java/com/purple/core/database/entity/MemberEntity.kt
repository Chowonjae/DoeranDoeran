package com.purple.core.database.entity

import androidx.room.Entity
import java.time.LocalDateTime

@Entity(
    tableName = "members",
    primaryKeys = ["userId"],
)
data class MemberEntity(
    val birth: LocalDateTime?,
    val userId: Long,
    val profileUrl: String?,
)
