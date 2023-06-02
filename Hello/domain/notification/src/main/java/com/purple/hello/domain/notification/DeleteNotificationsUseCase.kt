package com.purple.hello.domain.notification

import com.purple.hello.data.notification.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {

    suspend operator fun invoke() =
        notificationRepository.deleteNotifications()
}
