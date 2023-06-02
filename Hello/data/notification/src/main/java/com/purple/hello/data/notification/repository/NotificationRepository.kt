package com.purple.hello.data.notification.repository

import com.purple.core.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<List<Notification>>

    suspend fun deleteNotifications()
}
