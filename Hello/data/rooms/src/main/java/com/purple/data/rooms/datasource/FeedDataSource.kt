package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.response.QuestionResponse
import com.purple.data.rooms.model.response.feed.CreateFeedResponse
import com.purple.data.rooms.model.response.feed.DateFeedResponse
import retrofit2.Response
import java.io.File
import java.time.LocalDateTime

interface FeedDataSource {

    suspend fun getDateQuestion(
        roomId: Long,
        date: LocalDateTime,
    ): Response<QuestionResponse>

    suspend fun getDateFeed(
        roomId: Long,
        date: LocalDateTime,
    ): Response<List<DateFeedResponse>>


    suspend fun postFeed(
        userRoomId: Long,
        image: File,
    ): Response<CreateFeedResponse>
}
