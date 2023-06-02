package com.purple.hello.data.user.datasource

import com.purple.hello.data.user.model.ProfileSettingRequest
import com.purple.hello.data.user.model.UserResponse
import com.purple.hello.data.user.service.UserService
import retrofit2.Response
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService,
) : UserDataSource {

    override suspend fun getUserInfo(): Response<UserResponse> =
        userService.getUserInfo()

    override suspend fun setProfile(profileUrl: String, birth: String) =
        userService.setProfile(ProfileSettingRequest(profileUrl, birth))

    override suspend fun sendSafeAlarm(): Response<String> =
        userService.sendSafeAlarm()
}
