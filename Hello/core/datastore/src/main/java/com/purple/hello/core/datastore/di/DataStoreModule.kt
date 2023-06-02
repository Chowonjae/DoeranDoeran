package com.purple.hello.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.purple.hello.core.datastore.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesAccountPreferencesDataStore(
        @ApplicationContext context: Context,
        accountDataSerializer: AccountDataSerializer,
    ): DataStore<AccountData> =
        DataStoreFactory.create(
            serializer = accountDataSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile("account_data.pb")
        }

    @Provides
    @Singleton
    fun providesUserInfoPreferencesDataStore(
        @ApplicationContext context: Context,
        userInfoDataSerializer: UserInfoDataSerializer,
    ): DataStore<UserInfoData> =
        DataStoreFactory.create(
            serializer = userInfoDataSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile("user_info_data.pb")
        }

    @Provides
    @Singleton
    fun providesDeviceInfoPreferencesDataStore(
        @ApplicationContext context: Context,
        deviceDataSerializer: DeviceDataSerializer,
    ): DataStore<DeviceData> =
        DataStoreFactory.create(
            serializer = deviceDataSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile("device_data.pb")
        }
}
