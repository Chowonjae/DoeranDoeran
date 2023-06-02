package com.purple.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.purple.core.database.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    suspend fun insertNotificationEntity(notificationEntity: NotificationEntity)

    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("DELETE FROM notifications")
    suspend fun deleteAllNotifications()
}
