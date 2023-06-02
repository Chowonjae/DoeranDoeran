package com.purple.hello.feature.rooms.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.purple.core.model.Member
import com.purple.hello.feature.groups.R

@Composable
fun MemberProfileItem(
    modifier: Modifier = Modifier,
    member: Member,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .height(80.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(member.profileUrl)
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .crossfade(true)
                .build(),
            contentDescription = "${member.nickName}의 프로필",
        )
        Text(
            text = member.nickName,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun PreviewMemberProfileItem() {
    MemberProfileItem(
        member = Member(
            0L,
            "abc",
            "https://doeran.s3.ap-northeast-2.amazonaws.com/feed/0beca7f1-764f-48d4-b5da-94a8d2b728c1%EC%9D%B4%EA%B2%8C%EB%90%9C%EB%8B%A4%EA%B3%A0.png",
        ),
    )
}
