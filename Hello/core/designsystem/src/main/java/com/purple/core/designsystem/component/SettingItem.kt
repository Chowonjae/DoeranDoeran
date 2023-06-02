package com.purple.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography
import com.purple.core.model.type.SettingItemType

@Composable
fun SettingItem(
    onClick: () -> Unit,
    content: SettingItemType,
) {
    HiTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable(onClick = onClick)
                .background(Color.Unspecified)
                .padding(all = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically,
            ) {
                SettingItemContent(content)
                if (content.isButtonIcon) {
                    Icon(
                        imageVector = HiIcons.ArrowRight,
                        contentDescription = "더 보기",
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingItemContent(
    content: SettingItemType,
) {
    if (content.subText != "") {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            Arrangement.SpaceBetween,
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Text(
                    text = content.mainText,
                    color = if (content.isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface, // ktlint-disable max-line-length
                    style = HiTypography.bodyLarge,
                )
            }
            Box(contentAlignment = Alignment.CenterStart) {
                Text(
                    text = content.subText,
                    color = MaterialTheme.colorScheme.outline,
                    style = HiTypography.bodyMedium,
                )
            }
        }
    } else {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxHeight(),
        ) {
            Text(
                text = content.mainText,
                color = if (content.isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface, // ktlint-disable max-line-length
                style = HiTypography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingItem() {
    HiTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingItem(
                onClick = {},
                SettingItemType.CHANGE_ROOM_NAME,
            )
        }
    }
}
