package com.purple.hello.data.user.repository

import com.purple.core.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUserInfo()

    fun getUserId(): Flow<Long>

    suspend fun setProfile(profileUrl: String, birth: String)

    fun getProfile(): Flow<Profile>

    suspend fun sendSafeAlarm(): Result<String>
}
