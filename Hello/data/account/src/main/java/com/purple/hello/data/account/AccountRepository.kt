package com.purple.hello.data.account

import com.purple.core.database.HiDatabase
import com.purple.hello.core.datastore.AccountDataStore
import com.purple.hello.core.datastore.DeviceDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val remoteAccountDataSource: RemoteAccountDataSource,
    private val accountDataStore: AccountDataStore,
    private val deviceDataStore: DeviceDataStore,
    private val hiDatabase: HiDatabase,
) {
    suspend fun loginWithGoogle(idToken: String) {
        remoteAccountDataSource.loginWithGoogle(idToken, deviceDataStore.deviceToken.first())
    }

    suspend fun loginWithKakao(accessToken: String) {
        remoteAccountDataSource.loginWithKakao(accessToken, deviceDataStore.deviceToken.first())
    }

    suspend fun logout() {
        hiDatabase.clearAllTables()
        remoteAccountDataSource.logout()
    }

    fun isLoggedIn(): Flow<Boolean> = accountDataStore.isLoggedIn()
}
