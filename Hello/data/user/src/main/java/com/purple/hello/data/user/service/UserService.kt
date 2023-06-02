package com.purple.hello.data.user.service

import com.purple.hello.data.user.model.ProfileSettingRequest
import com.purple.hello.data.user.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserService {

    @GET("user/user-info")
    suspend fun getUserInfo(): Response<UserResponse>

    @PUT("user/user-info")
    suspend fun setProfile(
        @Body requestForProfileSetting: ProfileSettingRequest,
    ): Response<Void>

    @GET("room/no-response-notification")
    suspend fun sendSafeAlarm(): Response<String>

}
