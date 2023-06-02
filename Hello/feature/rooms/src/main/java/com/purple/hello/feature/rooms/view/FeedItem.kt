package com.purple.hello.feature.rooms.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.purple.core.designsystem.component.HiIconButton
import com.purple.core.model.Feed
import com.purple.core.model.Member
import com.purple.hello.feature.groups.R

@Composable
internal fun FeedItem(
    feed: Feed,
) {
    val isClosed = remember { mutableStateOf(true) }
    val modifier = remember(isClosed.value) {
        mutableStateOf(
            if (isClosed.value) {
                Modifier.fillMaxWidth().aspectRatio(1f)
            } else {
                Modifier.fillMaxWidth()
            },
        )
    }

    Profile(author = feed.author)
    Box {
        HiIconButton(
            modifier = Modifier.align(Alignment.TopEnd).zIndex(1f),
            onClick = { isClosed.value = !isClosed.value },
            icon = {
                if (isClosed.value) {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                } else {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                }
            },
        )
        AsyncImage(
            modifier = modifier.value,
            model = ImageRequest.Builder(LocalContext.current)
                .data(feed.headerImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
private fun Profile(
    author: Member,
) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .height(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(author.profileUrl)
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .crossfade(true)
                .build(),
            contentDescription = "${author.nickName}의 프로필",
            contentScale = ContentScale.FillBounds,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = author.nickName,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview
@Composable
private fun PreviewFeedItem() {
    FeedItem(
        Feed(
            feedId = 0L,
            headerImageUrl = "https://doeran.s3.ap-northeast-2.amazonaws.com/feed/21/921b73a5-aa05-4144-8dbe-34b33c18bff5image.jpg",
            content = null,
            author = Member(
                id = 0L,
                nickName = "홍길도",
                profileUrl = "https://doeran.s3.ap-northeast-2.amazonaws.com/profile/zodiac/tiger.png",
            ),
        ),
    )
}
