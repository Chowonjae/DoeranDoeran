package com.purple.hello.data.account

import com.purple.hello.core.datastore.AccountDataStore
import com.purple.hello.core.datastore.UserDataStore
import com.purple.hello.core.network.AccountService
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val accountService: AccountService,
    private val accountDataStore: AccountDataStore,
    private val userDataStore: UserDataStore,
) {

    suspend fun loginWithGoogle(idToken: String, deviceToken: String) {
        val responseHeader = accountService.loginWithGoogle(idToken, deviceToken).headers()
        accountDataStore.setToken(
            responseHeader["Access-Token"] ?: "",
            responseHeader["Refresh-Token"] ?: "",
        )
    }

    suspend fun loginWithKakao(accessToken: String, deviceToken: String) {
        val responseHeader = accountService.loginWithKakao(accessToken, deviceToken).headers()
        accountDataStore.setToken(
            responseHeader["Access-Token"] ?: "",
            responseHeader["Refresh-Token"] ?: "",
        )
    }

    suspend fun logout() {
        accountDataStore.clearToken()
        userDataStore.clearUserId()
    }
}
