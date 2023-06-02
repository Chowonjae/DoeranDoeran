package com.purple.hello.feature.setting.profile

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.purple.core.designsystem.component.HiDropDownTextField
import com.purple.core.designsystem.component.HiTopAppBar
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.LocalGradientColors
import com.purple.hello.feature.setting.profile.viewmodel.ProfileSettingViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileSettingRoute(
    profileSettingViewModel: ProfileSettingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onClickRooms: () -> Unit,
) {
    val isFirst = profileSettingViewModel.isFirst
    val profile by profileSettingViewModel.profile.collectAsState()

    val currentYear = LocalDate.now().year
    val years = (1920..currentYear).toList().map { it.toString() }
    var selectedYear by remember(profile.birth) { mutableStateOf(profile.birth?.year ?: currentYear) }
    var selectedMonth by remember(profile.birth) { mutableStateOf(profile.birth?.monthValue ?: LocalDate.now().monthValue) } // ktlint-disable wrapping
    var selectedDay by remember(profile.birth) { mutableStateOf(profile.birth?.dayOfMonth ?: 1) }
    val days = (1..getDaysInMonth(selectedYear.toInt(), selectedMonth.toInt())).toList().map { it.toString() }

    val selectedBirth = LocalDate.of(selectedYear, selectedMonth, selectedDay)
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var selectedImageUrl by remember(isFirst, profile) {
        mutableStateOf(
            if (!isFirst) profile.profileUrl ?: coilUrl else coilUrl,
        )
    }

    HiTheme {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(LocalGradientColors.current.top, LocalGradientColors.current.bottom),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY,
                    ),
                ),
        ) {
            ProfileSettingAppBar(
                isFirst = isFirst,
                onClickSave = {
                    profileSettingViewModel.setProfile(
                        newProfileUrl = selectedImageUrl,
                        newBirth = selectedBirth.format(dateFormatter),
                    )
                    onClickRooms()
                },
                onBackClick = onBackClick,
            )
            ProfileSettingScreen(
                selectedImageUrl = selectedImageUrl,
                changeSelectedImage = {
                    selectedImageUrl = it
                },
                years = years,
                days = days,
                selectedYear = selectedYear,
                selectedMonth = selectedMonth,
                selectedDay = selectedDay,
                changeYear = {
                    selectedYear = it
                },
                changeMonth = {
                    selectedMonth = it
                },
                changeDay = {
                    selectedDay = it
                },
            )
        }
    }
}

@Composable
private fun ProfileSettingScreen(
    selectedImageUrl: String,
    changeSelectedImage: (String) -> Unit,
    years: List<String>,
    days: List<String>,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    changeYear: (Int) -> Unit,
    changeMonth: (Int) -> Unit,
    changeDay: (Int) -> Unit,
) {
    val gridItemList = listOf(
        coilUrl + "rat.png", coilUrl + "cow.png", coilUrl + "tiger.png", coilUrl + "rabbit.png",
        coilUrl + "dragon.png", coilUrl + "snake.png", coilUrl + "horse.png", coilUrl + "sheep.png",
        coilUrl + "monkey.png", coilUrl + "rooster.png", coilUrl + "dog.png", coilUrl + "pig.png",
    )
    val scrollState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Unspecified),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(selectedImageUrl)
                    .placeholder(R.drawable.profile_placeholder)
                    .error(R.drawable.profile_placeholder)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = "ProfileImage",
            )
        }
        SelectBirth(
            years = years,
            days = days,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            selectedDay = selectedDay,
            changeYear = changeYear,
            changeMonth = changeMonth,
            changeDay = changeDay,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = scrollState,
            contentPadding = PaddingValues(all = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(gridItemList.size) { index ->
                    val imageUrl = gridItemList[index]

                    Card(
                        modifier = Modifier
                            .background(Color.Unspecified)
                            .padding(2.dp)
                            .fillMaxWidth()
                            .shadow(
                                elevation = 2.dp,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                changeSelectedImage(imageUrl)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .placeholder(R.drawable.profile_placeholder)
                                .error(R.drawable.profile_placeholder)
                                .crossfade(true)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            contentDescription = "",
                        )
                    }
                }
            },
        )
    }
}

@Composable
private fun SelectBirth(
    years: List<String>,
    days: List<String>,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    changeYear: (Int) -> Unit,
    changeMonth: (Int) -> Unit,
    changeDay: (Int) -> Unit,
) {
    val months = (1..12).toList().map { it.toString() }
    var yearExpanded by remember { mutableStateOf(false) }
    var monthExpanded by remember { mutableStateOf(false) }
    var dayExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
    ) {
        Text(
            text = "생년월일을 선택해주세요!",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    ) {
        Row {
            Box(
                modifier = Modifier
                    .weight(1.25f)
                    .height(50.dp),
            ) {
                HiDropDownTextField(
                    value = selectedYear.toString(),
                    onValueChange = { changeYear(it) },
                    iconClick = { yearExpanded = true },
                    expanded = yearExpanded,
                    onDismiss = { yearExpanded = false },
                    content = years,
                    itemClick = {
                        changeYear(it)
                        yearExpanded = false
                    },
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .weight(1f)
                    .height(50.dp),
            ) {
                HiDropDownTextField(
                    value = selectedMonth.toString(),
                    onValueChange = { changeMonth(it) },
                    iconClick = { monthExpanded = true },
                    expanded = monthExpanded,
                    onDismiss = { monthExpanded = false },
                    content = months,
                    itemClick = {
                        changeMonth(it)
                        monthExpanded = false
                    },
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .weight(1f)
                    .height(50.dp),
            ) {
                HiDropDownTextField(
                    value = selectedDay.toString(),
                    onValueChange = { changeDay(it) },
                    iconClick = { dayExpanded = true },
                    expanded = dayExpanded,
                    onDismiss = { dayExpanded = false },
                    content = days,
                    itemClick = {
                        changeDay(it)
                        dayExpanded = false
                    },
                )
            }
        }
    }
}

private const val coilUrl = "https://doeran.s3.ap-northeast-2.amazonaws.com/profile/zodiac/"

private fun getDaysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

@Composable
private fun ProfileSettingAppBar(
    isFirst: Boolean,
    onClickSave: () -> Unit,
    onBackClick: () -> Unit,
) {
    HiTopAppBar(
        title = "프로필 설정",
        navigationIcon = HiIcons.ArrowBack,
        navigationIconContentDescription = "뒤로 가기",
        actions = {
            Box(
                modifier = Modifier
                    .background(Color.Unspecified)
                    .padding(all = 16.dp)
                    .clickable { onClickSave() },
            ) { Text(text = "저장하기") }
        },
        onNavigationClick = { onBackClick() },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewProfileSetting() {
    ProfileSettingRoute(onBackClick = {}, onClickRooms = {})
}
