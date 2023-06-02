package com.purple.hello.core.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AccountService {
    @GET("account/login/google")
    suspend fun loginWithGoogle(
        @Header("id-token") idToken: String,
        @Header("device-token") deviceToken: String,
    ): Response<Void>

    @GET("account/login/kakao")
    suspend fun loginWithKakao(
        @Header("id-token") accessToken: String,
        @Header("device-token") deviceToken: String,
    ): Response<Void>

    @GET("account/reissue")
    fun reIssue(
        @Header("Access-Token") accessToken: String,
        @Header("Refresh-Token") refreshToken: String,
    ): Call<Void>
}
