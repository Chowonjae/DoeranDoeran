package com.purple.hello.feature.setting.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.component.SettingItem
import com.purple.core.designsystem.dialog.HiAlertDialog
import com.purple.core.designsystem.dialog.HiInputDialog
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.createInputDataByInputType
import com.purple.core.model.type.DeleteDialogType
import com.purple.core.model.type.InputDialogType
import com.purple.core.model.type.SettingItemType
import com.purple.core.model.type.SettingItemType.Companion.getItemsForHost
import com.purple.core.model.type.SettingItemType.Companion.getItemsForUser
import com.purple.hello.feature.setting.room.viewmodel.RoomSettingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoomSettingRoute(
    roomSettingViewModel: RoomSettingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onClickRooms: () -> Unit,
) {
    var shouldShowChangeRoomNameDialog by remember { mutableStateOf(false) }
    var shouldShowChangeUserNameDialog by remember { mutableStateOf(false) }
    var shouldShowChangePasswordDialog by remember { mutableStateOf(false) }
    var shouldShowExitRoomDialog by remember { mutableStateOf(false) }
    var shouldShowDeleteRoomDialog by remember { mutableStateOf(false) }

    val roomSettingInfo by roomSettingViewModel.roomSettingInfo.collectAsState()
    val isHost = roomSettingInfo.role == "ROLE1"

    HiTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
        ) {
            RoomSettingAppBar(onBackClick = onBackClick)
            RoomSettingScreen(
                isHost = isHost,
                onClick = { itemType ->
                    when (itemType) {
                        SettingItemType.CHANGE_ROOM_NAME -> {
                            shouldShowChangeRoomNameDialog = true
                        }
                        SettingItemType.CHANGE_NAME -> {
                            shouldShowChangeUserNameDialog = true
                        }
                        SettingItemType.CHANGE_PASSWORD -> {
                            shouldShowChangePasswordDialog = true
                        }
                        SettingItemType.EXIT_GROUP -> {
                            shouldShowExitRoomDialog = true
                        }
                        SettingItemType.DELETE_GROUP -> {
                            shouldShowDeleteRoomDialog = true
                        }
                        else -> {}
                    }
                },
            )
        }
        when {
            shouldShowChangeRoomNameDialog -> {
                ChangeRoomNameDialog(
                    nowRoomName = roomSettingInfo.roomName,
                    onDismiss = {
                        shouldShowChangeRoomNameDialog = false
                    },
                    onConfirm = roomSettingViewModel::updateRoomName,
                    onBackClick = onBackClick,
                )
            }
            shouldShowChangeUserNameDialog -> {
                ChangeUserNameDialog(
                    nowUserName = roomSettingInfo.userName,
                    onDismiss = {
                        shouldShowChangeUserNameDialog = false
                    },
                    onConfirm = roomSettingViewModel::updateUserName,
                    onBackClick = onBackClick,
                )
            }
            shouldShowChangePasswordDialog -> {
                ChangePasswordDialog(
                    nowPasswordQuestion = roomSettingInfo.passwordQuestion,
                    onDismiss = {
                        shouldShowChangePasswordDialog = false
                    },
                    onConfirm = roomSettingViewModel::updateRoomPassword,
                    onBackClick = onBackClick,
                )
            }
            shouldShowExitRoomDialog -> {
                ExitRoomDialog(
                    onDismiss = {
                        shouldShowExitRoomDialog = false
                    },
                    onDelete = roomSettingViewModel::exitRoom,
                    onClickRooms = onClickRooms,
                )
            }
            shouldShowDeleteRoomDialog -> {
                DeleteRoomDialog(
                    onDismiss = {
                        shouldShowDeleteRoomDialog = false
                    },
                    onDelete = roomSettingViewModel::deleteRoom,
                    onClickRooms = onClickRooms,
                )
            }
        }
    }
}

@Composable
private fun RoomSettingScreen(
    isHost: Boolean,
    onClick: (SettingItemType) -> Unit,
) {
    val settingItems = if (isHost) getItemsForHost() else getItemsForUser()

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
private fun ChangeRoomNameDialog(
    nowRoomName: String,
    onDismiss: () -> Unit,
    onConfirm: (newRoomName: String) -> Unit,
    onBackClick: () -> Unit,
) {
    val newRoomName = createInputDataByInputType(InputDialogType.EDIT_ROOM_NAME, inputValue = nowRoomName)

    HiInputDialog(
        questionContent = listOf(newRoomName),
        onDismiss = { onDismiss() },
        onConfirm = {
            onConfirm(newRoomName.inputValue)
            onDismiss()
            onBackClick()
        },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ChangeUserNameDialog(
    nowUserName: String,
    onDismiss: () -> Unit,
    onConfirm: (newUserName: String) -> Unit,
    onBackClick: () -> Unit,
) {
    val newUserName = createInputDataByInputType(InputDialogType.EDIT_NAME, inputValue = nowUserName)

    HiInputDialog(
        questionContent = listOf(newUserName),
        onDismiss = { onDismiss() },
        onConfirm = {
            onConfirm(newUserName.inputValue)
            onDismiss()
            onBackClick()
        },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ChangePasswordDialog(
    nowPasswordQuestion: String,
    onDismiss: () -> Unit,
    onConfirm: (newPasswordQuestion: String, newPassword: String) -> Unit,
    onBackClick: () -> Unit,
) {
    val newPasswordQuestion = createInputDataByInputType(
        InputDialogType.EDIT_QUESTION_PASSWORD,
        inputValue = nowPasswordQuestion,
    )
    val newPassword = createInputDataByInputType(InputDialogType.EDIT_PASSWORD, inputValue = "")
    HiInputDialog(
        questionContent = listOf(newPasswordQuestion, newPassword),
        onDismiss = { onDismiss() },
        onConfirm = {
            onConfirm(newPasswordQuestion.inputValue, newPassword.inputValue)
            onDismiss()
            onBackClick()
        },
        confirmButtonText = "변경하기",
        dismissButtonText = "취소",
    )
}

@Composable
private fun ExitRoomDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onClickRooms: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = { onDismiss() },
        onDelete = {
            onDelete()
            onDismiss()
            onClickRooms()
        },
        content = DeleteDialogType.EXIT_GROUP,
    )
}

@Composable
private fun DeleteRoomDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onClickRooms: () -> Unit,
) {
    HiAlertDialog(
        onDismiss = { onDismiss() },
        onDelete = {
            onDelete()
            onDismiss()
            onClickRooms()
        },
        content = DeleteDialogType.DELETE_GROUP,
    )
}

@Composable
private fun RoomSettingAppBar(
    onBackClick: () -> Unit,
) {
    HiTopAppBar(
        title = "방 설정",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {},
        onNavigationClick = { onBackClick() },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewRoomSettingScreen() {
    RoomSettingRoute(onBackClick = {}, onClickRooms = {})
}
