package com.purple.hello.feature.rooms

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purple.core.designsystem.component.HiIconButton
import com.purple.core.designsystem.component.HiOverlayLoadingWheel
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.dialog.HiInputDialog
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.LocalGradientColors
import com.purple.core.designsystem.utils.multipleEventsCutter
import com.purple.core.model.createInputDataByInputType
import com.purple.core.model.type.InputDialogType
import com.purple.hello.feature.rooms.state.RoomsUiState
import com.purple.hello.feature.rooms.view.RoomItem
import com.purple.hello.feature.rooms.viewmodel.RoomsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun RoomsRoute(
    roomsViewModel: RoomsViewModel = hiltViewModel(),
    onClickRoom: (roomId: Long) -> Unit,
    onClickAppSetting: () -> Unit,
    onClickNotification: () -> Unit,
) {
    val uiState by roomsViewModel.roomsUiState.collectAsState()
    val isRoomsLoading = uiState is RoomsUiState.Loading
    var shouldShowAddDialog by remember { mutableStateOf(false) }
    val state = rememberLazyGridState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            roomsViewModel.fetchRoom()
        }
    }

    Column(Modifier.fillMaxSize()) {
        RoomsAppBar(
            onClickAppSetting = onClickAppSetting,
            onClickNotification = onClickNotification,
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            LazyVerticalGrid(
                columns = Adaptive(300.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
                state = state,
            ) {
                roomsScreen(
                    roomsState = uiState,
                    onClick = { onClickRoom(it) },
                )
            }
            OpenAddRoomDialogButton(
                onClick = {
                    shouldShowAddDialog = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
            )
        }
    }

    if (shouldShowAddDialog) {
        multipleEventsCutter { manager ->
            AddRoomDialog(
                onDismiss = {
                    shouldShowAddDialog = false
                },
                onConfirm = { roomName, userName, question, password ->
                    manager.processEvent {
                        coroutineScope.launch(Dispatchers.IO) {
                            roomsViewModel.createRoom(roomName, userName, question, password) { roomId ->
                                launch(Dispatchers.Main) {
                                    onClickRoom(roomId)
                                }
                            }
                        }
                    }
                },
            )
        }
    }
    AnimatedVisibility(
        visible = isRoomsLoading,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
        ) + fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            HiOverlayLoadingWheel(
                contentDesc = "loading",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun RoomsAppBar(
    onClickAppSetting: () -> Unit,
    onClickNotification: () -> Unit,
) {
    HiTopAppBar(
        title = "도란도란",
        actions = {
            HiIconButton(
                onClick = { onClickNotification() },
                icon = {
                    Icon(
                        imageVector = HiIcons.Notifications,
                        contentDescription = "",
                    )
                },
            )
            HiIconButton(
                onClick = { onClickAppSetting() },
                icon = {
                    Icon(
                        imageVector = HiIcons.MoreVert,
                        contentDescription = "",
                    )
                },
            )
        },
    )
}

@Composable
private fun OpenAddRoomDialogButton(
    onClick: () -> Unit,
    modifier: Modifier,
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        shape = CircleShape,
    ) {
        Icon(
            imageVector = HiIcons.AddRoom,
            contentDescription = "addRoom",
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun LazyGridScope.roomsScreen(
    roomsState: RoomsUiState,
    onClick: (roomId: Long) -> Unit,
) {
    when (roomsState) {
        RoomsUiState.Loading -> Unit
        is RoomsUiState.Success -> {
            items(roomsState.rooms, key = { it.roomId }) { room ->
                Column {
                    RoomItem(
                        roomName = room.roomName,
                        members = room.members,
                        onClick = { onClick(room.roomId) },
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(Color.Gray),
                    )
                }
            }
        }
        is RoomsUiState.Error -> Unit
    }
}

@Composable
private fun ErrorScreen() {
}

@Composable
private fun AddRoomDialog(
    onDismiss: () -> Unit,
    onConfirm: (roomName: String, userName: String, question: String, password: String) -> Unit,
) {
    val roomName = createInputDataByInputType(type = InputDialogType.ROOM_NAME, inputValue = "")
    val userName = createInputDataByInputType(type = InputDialogType.NAME, inputValue = "")
    val question = createInputDataByInputType(type = InputDialogType.QUESTION_PASSWORD, inputValue = "")
    val password = createInputDataByInputType(type = InputDialogType.CREATE_PASSWORD, inputValue = "")

    HiInputDialog(
        questionContent = listOf(roomName, userName, question, password),
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(roomName.inputValue, userName.inputValue, question.inputValue, password.inputValue) },
        confirmButtonText = "생성하기",
        dismissButtonText = "취소",
    )
}

@Preview
@Composable
private fun PreviewRoomScreen() {
    HiTheme {
        Box(
            Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(LocalGradientColors.current.top, LocalGradientColors.current.bottom),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY,
                    ),
                )
                .fillMaxSize(),
        ) {
//            RoomsRoute()
        }
    }
}
