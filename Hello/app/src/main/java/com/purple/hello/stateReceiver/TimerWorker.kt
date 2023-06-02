package com.purple.hello.stateReceiver

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.purple.hello.domain.user.SendSafeAlarmUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TimerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sendSafeAlarm: SendSafeAlarmUseCase,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val result = sendSafeAlarm() // sendSafeAlarm 호출
        return if (result.isSuccess) {
            Result.success()
        } else {
            Result.failure()
        }
    }
}
