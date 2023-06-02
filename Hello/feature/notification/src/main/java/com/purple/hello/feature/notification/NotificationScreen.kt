package com.purple.hello.feature.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.component.NotificationItem
import com.purple.core.designsystem.dialog.HiAlertDialog
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.Notification
import com.purple.core.model.type.DeleteDialogType
import com.purple.hello.feature.notification.viewmodel.NotificationViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationRoute(
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    var shouldShowDeleteNotificationsDialog by remember { mutableStateOf(false) }
    val notificationList by notificationViewModel.notifications.collectAsState()

    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            NotificationAppBar(
                notificationList = notificationList,
                onBackClick = onBackClick,
                deleteNotificationsDialog = {
                    shouldShowDeleteNotificationsDialog = true
                },
            )
            NotificationScreen(notificationList = notificationList)
        }
        if (shouldShowDeleteNotificationsDialog) {
            DeleteNotificationsDialog(
                onDismiss = {
                    shouldShowDeleteNotificationsDialog = false
                },
                onDelete = { notificationViewModel.deleteNotifications() },
            )
        }
    }
}

@Composable
private fun NotificationScreen(
    notificationList: List<Notification>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn {
            itemsIndexed(notificationList) { index, it ->
                NotificationItem(
                    titleContent = it.title,
                    bodyContent = it.body,
                    timeContent = getCurrentDate(it.timestamp),
                )
                if (index < notificationList.lastIndex) {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}

private fun getCurrentDate(
    timestamp: Long,
): String {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd  HH:mm", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}

@Composable
private fun DeleteNotificationsDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = { onDismiss() },
        onDelete = {
            onDelete()
            onDismiss()
        },
        content = DeleteDialogType.DELETE_NOTIFICATIONS,
    )
}

@Composable
private fun NotificationAppBar(
    notificationList: List<Notification>,
    onBackClick: () -> Unit,
    deleteNotificationsDialog: () -> Unit,
) {
    HiTopAppBar(
        title = "알림 목록",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {
            if (notificationList.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .background(Color.Unspecified)
                        .padding(all = 16.dp)
                        .clickable { deleteNotificationsDialog() },
                ) { Text(text = "모두 비우기") }
            } else {
                Box(
                    modifier = Modifier
                        .background(Color.Unspecified)
                        .padding(all = 16.dp),
                ) {
                    Text(
                        text = "모두 비우기",
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
        },
        onNavigationClick = { onBackClick() },
    )
}

@Preview
@Composable
private fun PreviewNotificationScreen() {
    NotificationRoute(onBackClick = {})
}
