package com.purple.core.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun providesHiDatabase(
        @ApplicationContext context: Context,
    ): HiDatabase = Room.databaseBuilder(
        context,
        HiDatabase::class.java,
        "hi-database",
    ).build()
}
