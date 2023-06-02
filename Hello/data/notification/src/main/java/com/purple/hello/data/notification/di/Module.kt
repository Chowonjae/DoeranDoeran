package com.purple.hello.data.notification.di

import com.purple.hello.data.notification.repository.NotificationRepository
import com.purple.hello.data.notification.repository.NotificationRepositoryImpl
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface Module {

    @Binds
    fun bindsNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl,
    ): NotificationRepository
}
