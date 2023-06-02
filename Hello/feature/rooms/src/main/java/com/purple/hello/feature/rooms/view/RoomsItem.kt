package com.purple.hello.feature.rooms.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.*
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.model.Member
import com.purple.hello.feature.groups.R
import com.purple.hello.feature.rooms.fake.FakeFactory
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoomItem(
    roomName: String,
    members: List<Member>,
    onClick: () -> Unit,
) {
    val isBirthDay by remember(members) { mutableStateOf(checkIsBirthDay(members)) }

    val wallpaperComposition by rememberLottieComposition(
        if (isBirthDay) {
            LottieCompositionSpec.RawRes(R.raw.party_room_wallpaper)
        } else {
            LottieCompositionSpec.RawRes(R.raw.k_room_wallpaper)
        },
    )
    val birthParticleComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.happy_birthday),
    )

    val wallpaperAnimate = rememberLottieAnimatable()
    val birthAnimate = rememberLottieAnimatable()

    LaunchedEffect(wallpaperComposition) {
        wallpaperAnimate.animate(
            composition = wallpaperComposition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f,
        )
    }

    LaunchedEffect(birthParticleComposition) {
        if (isBirthDay) {
            birthAnimate.animate(
                composition = birthParticleComposition,
                clipSpec = LottieClipSpec.Frame(0, 1200),
                initialProgress = 0f,
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick() },
    ) {
        if (isBirthDay) {
            LottieAnimation(
                modifier = Modifier.fillMaxSize().zIndex(-1f),
                composition = birthParticleComposition,
                progress = { birthAnimate.progress },
                contentScale = ContentScale.Fit,
            )
        }
        LottieAnimation(
            modifier = Modifier.fillMaxSize().zIndex(-2f),
            composition = wallpaperComposition,
            progress = { wallpaperAnimate.progress },
            contentScale = ContentScale.Crop,
        )

        Column {
            Text(
                modifier = Modifier.padding(24.dp),
                text = roomName,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = members.joinToString(", ") { it.nickName },
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 40.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            members.forEach {
                AsyncImage(
                    modifier = Modifier.height(100.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.profileUrl)
                        .placeholder(R.drawable.profile_placeholder)
                        .error(R.drawable.profile_placeholder)
                        .crossfade(true)
                        .build(),
                    contentDescription = "${it.nickName}의 프로필",
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun checkIsBirthDay(members: List<Member>): Boolean {
    val today = LocalDate.now()
    return members.any { member ->
        member.birth?.let {
            it.month == today.month && it.dayOfMonth == today.dayOfMonth
        } ?: false
    }
}

@Preview
@Composable
private fun PreviewRoomItem() {
    val room = FakeFactory.makeRoom(1, "우리집")
    HiTheme {
//        RoomItem(
//            roomName = room.roomName,
//            members = room.members,
//            onClick = {},
//        )
    }
}
