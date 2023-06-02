package com.purple.hello.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserDataStore @Inject constructor(
    private val userProtoDataStore: DataStore<UserInfoData>,
) {
    val userId: Flow<Long> = userProtoDataStore.data.map {
        it.userId
    }

    suspend fun setUserId(userId: Long) {
        try {
            userProtoDataStore.updateData {
                it.copy {
                    this.userId = userId
                }
            }
        } catch (ioException: IOException) {
            Log.e("userProtoDataStore", "Failed to update id")
        }
    }

    suspend fun clearUserId() {
        userProtoDataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}
