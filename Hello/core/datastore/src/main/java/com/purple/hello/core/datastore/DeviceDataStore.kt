package com.purple.hello.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DeviceDataStore @Inject constructor(
    private val deviceProtoDataStore: DataStore<DeviceData>,
) {
    val deviceToken = deviceProtoDataStore.data.map { it.deviceToken }

    suspend fun setDeviceToken(deviceToken: String) {
        try {
            deviceProtoDataStore.updateData {
                it.copy {
                    this.deviceToken = deviceToken
                }
            }
        } catch (ioException: IOException) {
            Log.e("deviceProtoDataStore", "Failed to device token")
        }
    }
}
