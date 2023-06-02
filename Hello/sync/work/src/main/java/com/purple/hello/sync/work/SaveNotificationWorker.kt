package com.purple.hello.sync.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.purple.core.database.dao.NotificationDao
import com.purple.core.database.entity.NotificationEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SaveNotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationDao: NotificationDao,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.d(TAG, "do work")
        val title = inputData.getString("title") ?: ""
        val body = inputData.getString("body") ?: ""
        val timestamp = inputData.getLong("timestamp", 0L)

        try {
            notificationDao.insertNotificationEntity(
                NotificationEntity(
                    title = title,
                    body = body,
                    timestamp = timestamp,
                ),
            )
            Log.d(TAG, "NotificationEntity: $title")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
        Result.success()
    }

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_BODY = "body"
        const val KEY_TIMESTAMP = "timestamp"
        private const val TAG = "WorkManager"
    }
}
