package com.purple.hello.core.network.di

import com.purple.hello.core.network.AccountService
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
    fun provideAccountService(@Named("login") retrofit: Retrofit): AccountService =
        retrofit.create(AccountService::class.java)
}
