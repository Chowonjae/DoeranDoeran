package com.purple.hello.domain.notification

import com.purple.core.model.Notification
import com.purple.hello.data.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    operator fun invoke(): Flow<List<Notification>> = notificationRepository.getNotifications()
}
