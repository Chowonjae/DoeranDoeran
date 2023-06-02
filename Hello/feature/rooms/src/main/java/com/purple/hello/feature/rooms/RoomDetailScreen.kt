package com.purple.hello.feature.rooms

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.purple.core.designsystem.component.HiIconButton
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.LocalGradientColors
import com.purple.core.model.Member
import com.purple.core.model.Room
import com.purple.hello.feature.rooms.state.FeedUiState
import com.purple.hello.feature.rooms.view.FeedItem
import com.purple.hello.feature.rooms.view.MemberProfileItem
import com.purple.hello.feature.rooms.viewmodel.FeedViewModel
import com.purple.hello.feature.rooms.viewmodel.RoomsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun RoomDetailRoute(
    feedViewModel: FeedViewModel = hiltViewModel(),
    roomsViewModel: RoomsViewModel = hiltViewModel(),
    onClickCameraButton: (roomId: Long) -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
    onBackClick: () -> Unit,
) {
    val selectedRoom by roomsViewModel.selectedRoom.collectAsState()
    val roomCode by roomsViewModel.roomCode.collectAsState()
    val feedUiState by feedViewModel.feedUiState.collectAsState()
    val currentDate = remember { mutableStateOf(LocalDateTime.now()) }

    val isShowCalendar = remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }

    if (selectedRoom != null) {
        LaunchedEffect(currentDate.value) {
            feedViewModel.selectDate(currentDate.value)
            feedViewModel.fetchFeed(currentDate.value)
        }

        LaunchedEffect(selectedRoom) {
            currentDate.value = LocalDateTime.now()
            roomsViewModel.fetchRoomDetail()
        }

        LaunchedEffect(refreshing) {
            if (refreshing) {
                feedViewModel.fetchFeed(currentDate.value)
                refreshing = false
            }
        }
    }

    if (isShowCalendar.value) {
        CalendarDialog(
            state = rememberUseCaseState(visible = true, onCloseRequest = { isShowCalendar.value = false }),
            selection = CalendarSelection.Date { newDate ->
                currentDate.value = LocalDateTime.of(newDate, LocalTime.now())
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LocalGradientColors.current.top, LocalGradientColors.current.bottom),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY,
                ),
            ),
    ) {
        if (selectedRoom != null) {
            RoomDetailScreen(
                roomDetail = selectedRoom!!,
                onBackClick = onBackClick,
                onClickRoomSetting = {
                    onClickRoomSetting(it)
                },
                onClickCalendar = { isShowCalendar.value = true },
                roomCode = roomCode,
            )
            Divider()
            Question(feedUiState, currentDate.value)
            OpenCameraButton(
                feedUiState = feedUiState,
                onClick = {
                    onClickCameraButton(selectedRoom!!.roomId)
                },
                date = currentDate.value,
            )
            Divider()
            RoomFeedScreen(
                feedUiState = feedUiState,
                refreshing = refreshing,
                onRefreshing = {
                    refreshing = true
                },
            )
        }
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(Color.LightGray),
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Question(feedUiState: FeedUiState, date: LocalDateTime) {
    if (feedUiState is FeedUiState.Success) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "${date.monthValue}월 ${date.dayOfMonth}일의 질문",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = feedUiState.question ?: "아직 질문이 올라오지 않았어요",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun RoomDetailScreen(
    roomDetail: Room,
    onBackClick: () -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
    onClickCalendar: () -> Unit,
    roomCode: String,
) {
    Column {
        RoomDetailAppBar(
            onBackClick = onBackClick,
            onClickRoomSetting = {
                onClickRoomSetting(
                    roomDetail.roomId,
                )
            },
            onClickCalendar = onClickCalendar,
            roomName = roomDetail.roomName,
            roomCode = roomCode,
        )
        MembersViewInGroup(roomDetail.members)
    }
}

@Composable
private fun RoomDetailAppBar(
    roomName: String,
    onBackClick: () -> Unit,
    onClickRoomSetting: () -> Unit,
    onClickCalendar: () -> Unit,
    roomCode: String,
) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, roomCode)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current

    HiTopAppBar(
        title = roomName,
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로가기",
        onNavigationClick = { onBackClick() },
        actions = {
            HiIconButton(
                onClick = {
                    context.startActivity(shareIntent)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = HiIcons.PersonAdd),
                        contentDescription = "공유하기",
                    )
                },
            )
            HiIconButton(
                onClick = onClickCalendar,
                icon = {
                    Icon(
                        imageVector = HiIcons.Calendar,
                        contentDescription = "캘린더",
                    )
                },
            )
            HiIconButton(
                onClick = { onClickRoomSetting() },
                icon = {
                    Icon(
                        imageVector = HiIcons.Settings,
                        contentDescription = "설정",
                    )
                },
            )
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RoomFeedScreen(
    feedUiState: FeedUiState,
    refreshing: Boolean,
    onRefreshing: () -> Unit,
) {
    val state = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(refreshing, { onRefreshing() })

    when (feedUiState) {
        is FeedUiState.Error -> {
        }
        is FeedUiState.Loading -> {
        }
        is FeedUiState.Success -> {
            Box(
                Modifier
                    .pullRefresh(pullRefreshState),
            ) {
                LazyColumn(
                    modifier = Modifier.pullRefresh(pullRefreshState),
                    state = state,
                ) {
                    items(feedUiState.feeds, key = { it.feedId }) {
                        FeedItem(feed = it)
                    }
                }
                PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
            }
        }
    }
}

@Composable
private fun MembersViewInGroup(
    members: List<Member>,
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(members, key = { it.id }) { member ->
            MemberProfileItem(member = member)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun OpenCameraButton(
    feedUiState: FeedUiState,
    date: LocalDateTime,
    onClick: () -> Unit,
) {
    if (feedUiState is FeedUiState.Success) {
        if (
            date.toLocalDate() == LocalDate.now() &&
            feedUiState.isPossibleToUpload &&
            feedUiState.question != null
        ) {
            HiOutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = {
                    Text(
                        text = "사진 찍어서 올리기",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )
        }
    }
}
