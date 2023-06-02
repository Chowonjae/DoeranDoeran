package com.purple.core.database.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.purple.core.database.entity.FeedEntity
import com.purple.core.database.entity.MemberEntity
import com.purple.core.model.Feed
import com.purple.core.model.Member

data class FeedWithAuthor(
    @Embedded val feed: FeedEntity,
    @ColumnInfo(name = "nickName") val nickName: String,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId",
        entity = MemberEntity::class
    )
    val author: MemberEntity
)

@RequiresApi(Build.VERSION_CODES.O)
fun FeedWithAuthor.asExternalModel() = Feed(
    feedId = feed.feedId,
    headerImageUrl = feed.feedUrl,
    content = feed.content,
    author = Member(
        id = author.userId,
        nickName = nickName,
        profileUrl = author.profileUrl,
        birth = author.birth?.toLocalDate()
    )
)
