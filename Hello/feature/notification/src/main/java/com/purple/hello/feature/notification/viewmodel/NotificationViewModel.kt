package com.purple.hello.feature.notification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.core.model.Notification
import com.purple.hello.domain.notification.GetNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    getNotificationListFlow: GetNotificationUseCase,
) : ViewModel() {

    val notifications: StateFlow<List<Notification>> =
        getNotificationListFlow().stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList(),
        )
}
