package com.purple.data.rooms.di

import com.purple.data.rooms.service.FeedService
import com.purple.data.rooms.service.RoomService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideRoomService(@Named("default") retrofit: Retrofit): RoomService =
        retrofit.create(RoomService::class.java)

    @Provides
    fun provideFeedService(@Named("default") retrofit: Retrofit): FeedService =
        retrofit.create(FeedService::class.java)
}
