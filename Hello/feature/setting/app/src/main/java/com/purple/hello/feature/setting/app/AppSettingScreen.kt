package com.purple.hello.feature.setting.app

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.component.SettingItem
import com.purple.core.designsystem.dialog.HiAlertDialog
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.type.DeleteDialogType
import com.purple.core.model.type.SettingItemType
import com.purple.core.model.type.SettingItemType.Companion.getItemsForApp
import com.purple.hello.feature.setting.app.viewmodel.AppSettingViewModel

@Composable
fun AppSettingRoute(
    appSettingViewModel: AppSettingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    clearNav: () -> Unit,
    onClickProfileSetting: () -> Unit,
) {
    var shouldShowLogoutDialog by remember { mutableStateOf(false) }

    val appPackageName = "com.purple.hello"
    val appInfoUrl = "https://play.google.com/store/apps/details?id=$appPackageName"
    val policyUrl = "https://mysterious-emery-1f8.notion.site/1f8b49a6235f4fa993ab301d1b090c5b"
    val appInfoIntent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(appInfoUrl))
    val policyIntent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(policyUrl))

    val context = LocalContext.current

    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            AppSettingAppBar(onBackClick = onBackClick)
            AppSettingScreen(
                onClick = { itemType ->
                    when (itemType) {
                        SettingItemType.PROFILE_SETTING -> {
                            onClickProfileSetting()
                        }
                        SettingItemType.POLICY -> {
                            context.startActivity(policyIntent)
                        }
                        SettingItemType.APP_INFO -> {
                            context.startActivity(appInfoIntent)
                        }
                        SettingItemType.LOGOUT -> {
                            shouldShowLogoutDialog = true
                        }
                        else -> {}
                    }
                },
            )
        }
        when {
            shouldShowLogoutDialog -> {
                LogoutDialog(
                    onDismiss = {
                        shouldShowLogoutDialog = false
                    },
                    onDelete = {
                        appSettingViewModel.logout()
                        clearNav()
                    },
                )
            }
        }
    }
}

@Composable
private fun AppSettingScreen(
    onClick: (SettingItemType) -> Unit,
) {
    val settingItems = getItemsForApp()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column {
            settingItems.forEachIndexed { index, it ->
                SettingItem(
                    onClick = { onClick(it) },
                    content = it,
                )
                if (index < settingItems.lastIndex) {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}

@Composable
private fun LogoutDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = onDismiss,
        onDelete = onDelete,
        content = DeleteDialogType.LOGOUT,
    )
}

@Composable
private fun AppSettingAppBar(
    onBackClick: () -> Unit,
) {
    HiTopAppBar(
        title = "앱 설정",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {},
        onNavigationClick = { onBackClick() },
    )
}

@Preview
@Composable
private fun PreviewAppSettingScreen() {
    AppSettingRoute(onBackClick = {}, clearNav = {}, onClickProfileSetting = {})
}
