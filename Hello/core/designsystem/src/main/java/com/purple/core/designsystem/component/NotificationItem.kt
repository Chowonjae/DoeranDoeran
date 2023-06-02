package com.purple.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography

@Composable
fun NotificationItem(
    titleContent: String,
    bodyContent: String,
    timeContent: String,
) {
    HiTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Unspecified)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    Arrangement.SpaceBetween,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = titleContent,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = HiTypography.bodyMedium,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = bodyContent,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = HiTypography.bodyLarge,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = timeContent,
                            color = MaterialTheme.colorScheme.outline,
                            style = HiTypography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNotificationItem() {
    HiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NotificationItem(titleContent = "xxxxx 알람", bodyContent = "xxxxx 알림 입니다.", timeContent = "1시간 전")
        }
    }
}
