package com.purple.data.rooms.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.purple.data.rooms.model.response.QuestionResponse
import com.purple.data.rooms.model.response.feed.CreateFeedResponse
import com.purple.data.rooms.model.response.feed.DateFeedResponse
import com.purple.data.rooms.service.FeedService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class RemoteFeedDataSource @Inject constructor(
    private val feedService: FeedService,
) : FeedDataSource {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDateQuestion(roomId: Long, date: LocalDateTime): Response<QuestionResponse> =
        feedService.getDateQuestion(roomId, date.toLocalDate())

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDateFeed(roomId: Long, date: LocalDateTime): Response<List<DateFeedResponse>> =
        feedService.getDateFeeds(
            roomId,
            date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        )

    override suspend fun postFeed(userRoomId: Long, image: File): Response<CreateFeedResponse> {
        val requestFile = image.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val feedFile = MultipartBody.Part.createFormData("feedFile", image.name, requestFile)

        return feedService.postFeed(
            userRoomId = userRoomId.toInt(),
            feedFile = feedFile,
            content = null,
            feedType = null,
            feedUrl = null,
        )
    }
}
