package com.purple.data.rooms.service

import com.purple.data.rooms.model.response.QuestionResponse
import com.purple.data.rooms.model.response.feed.CreateFeedResponse
import com.purple.data.rooms.model.response.feed.DateFeedResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate
import java.util.*

interface FeedService {

    @GET("room/date-question")
    suspend fun getDateQuestion(
        @Query("roomId") roomId: Long,
        @Query("date") date: LocalDate,
    ): Response<QuestionResponse>

    @GET("room/date-feed")
    suspend fun getDateFeeds(
        @Query("roomId") roomId: Long,
        @Query("date") date: String,
    ): Response<List<DateFeedResponse>>

    @Multipart
    @POST("room/feed")
    suspend fun postFeed(
        @Part("content") content: String?,
        @Part feedFile: MultipartBody.Part,
        @Part("feedType") feedType: String?,
        @Part("feedUrl") feedUrl: String?,
        @Part("userRoomId") userRoomId: Int,
    ): Response<CreateFeedResponse>
}
