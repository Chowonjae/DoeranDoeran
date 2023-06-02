package com.purple.hello.data.user.datasource

import com.purple.hello.data.user.model.UserResponse
import retrofit2.Response

interface UserDataSource {

    suspend fun getUserInfo(): Response<UserResponse>

    suspend fun setProfile(
        profileUrl: String,
        birth: String,
    ): Response<Void>

    suspend fun sendSafeAlarm(): Response<String>
}
