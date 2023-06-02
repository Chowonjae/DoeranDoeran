package com.purple.core.database

import com.purple.core.database.dao.FeedDao
import com.purple.core.database.dao.NotificationDao
import com.purple.core.database.dao.RoomDao
import com.purple.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesRoomDao(
        database: HiDatabase,
    ): RoomDao = database.roomDao()

    @Provides
    fun providesUserDao(
        database: HiDatabase,
    ): UserDao = database.userDao()

    @Provides
    fun providesFeedDao(
        database: HiDatabase,
    ): FeedDao = database.feedDao()

    @Provides
    fun providesNotificationDao(
        database: HiDatabase,
    ): NotificationDao = database.notificationDao()
}
