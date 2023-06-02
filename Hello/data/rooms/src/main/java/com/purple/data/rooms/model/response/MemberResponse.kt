package com.purple.data.rooms.model.response

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity
import com.purple.hello.core.network.utils.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MembersResponse(
    val members: List<MemberResponse>,
    val roomId: Long,
)

@Serializable
data class MemberResponse(
    val name: String,
    val birth: String?,
    val profileUrl: String?,
    val userId: Long,
    val userRoomRole: String,
)

fun MemberResponse.asMemberRoomEntity(roomId: Long) = MemberRoomEntity(
    userId = userId,
    roomId = roomId,
    nickName = name,
    role = userRoomRole,
)

@RequiresApi(Build.VERSION_CODES.O)
fun MemberResponse.asMemberEntity() = MemberEntity(
    birth = birth?.toLocalDateTime(),
    userId = userId,
    profileUrl = profileUrl,
)
