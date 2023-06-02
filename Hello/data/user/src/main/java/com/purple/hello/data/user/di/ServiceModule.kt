package com.purple.hello.data.user.di

import com.purple.hello.data.user.service.UserService
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
    fun provideUserService(@Named("default") retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)
}
