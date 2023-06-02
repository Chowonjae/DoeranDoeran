package com.purple.core.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.purple.core.database.dao.FeedDao
import com.purple.core.database.dao.NotificationDao
import com.purple.core.database.dao.RoomDao
import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.*
import com.purple.core.database.utils.DateConverters
import com.purple.core.database.utils.LocalDateTimeTypeConverter
import com.purple.core.database.utils.LocalDateTypeConverter

@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [
        MemberRoomEntity::class,
        MemberEntity::class,
        RoomEntity::class,
        RoomCommonOptionsEntity::class,
        QuestionEntity::class,
        QuestionRoomCrossEntity::class,
        FeedEntity::class,
        NotificationEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    DateConverters::class,
    LocalDateTimeTypeConverter::class,
    LocalDateTypeConverter::class,
)
abstract class HiDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
    abstract fun feedDao(): FeedDao
    abstract fun notificationDao(): NotificationDao
}
