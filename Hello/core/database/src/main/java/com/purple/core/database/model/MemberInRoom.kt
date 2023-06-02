package com.purple.core.database.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Embedded
import androidx.room.Relation
import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity
import com.purple.core.model.Member

data class MemberInRoom(
    @Embedded val memberRoom: MemberRoomEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId",
    )
    val member: MemberEntity
)

@RequiresApi(Build.VERSION_CODES.O)
fun MemberInRoom.asExternalModel() = Member(
    id = memberRoom.userId,
    nickName = memberRoom.nickName,
    profileUrl = member.profileUrl,
    birth = member.birth?.toLocalDate()
)
