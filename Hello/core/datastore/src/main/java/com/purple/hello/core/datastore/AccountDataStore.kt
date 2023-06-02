package com.purple.hello.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AccountDataStore @Inject constructor(
    private val accountProtoDataStore: DataStore<AccountData>,
) {
    val accessToken = accountProtoDataStore.data.map { it.accessToken }
    val refreshToken = accountProtoDataStore.data.map { it.refreshToken }

    suspend fun setToken(accessToken: String, refreshToken: String) {
        try {
            accountProtoDataStore.updateData {
                it.copy {
                    this.accessToken = accessToken
                    this.refreshToken = refreshToken
                }
            }
        } catch (ioException: IOException) {
            Log.e("accountProtoDataStore", "Failed to update token")
        }
    }

    fun isLoggedIn(): Flow<Boolean> = accountProtoDataStore.data.map { accountData ->
        !accountData.accessToken.isNullOrBlank() && !accountData.refreshToken.isNullOrBlank()
    }

    suspend fun clearToken() {
        accountProtoDataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}
