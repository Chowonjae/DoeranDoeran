package com.purple.hello.data.notification.repository

import com.purple.core.database.dao.NotificationDao
import com.purple.core.model.Notification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao,
) : NotificationRepository {

    override fun getNotifications(): Flow<List<Notification>> =
        notificationDao.getAllNotifications().map { list ->
            list.map { it ->
                Notification(
                    title = it.title,
                    body = it.body,
                    timestamp = it.timestamp,
                )
            }
        }

    override suspend fun deleteNotifications() {
        notificationDao.deleteAllNotifications()
    }
}

