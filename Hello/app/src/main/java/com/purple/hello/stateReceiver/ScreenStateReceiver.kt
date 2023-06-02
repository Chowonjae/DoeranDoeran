package com.purple.hello.stateReceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.work.*
import java.util.concurrent.TimeUnit

class ScreenStateReceiver : BroadcastReceiver() {

    private var workManager: WorkManager? = null

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SCREEN_ON -> {
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT,
                )

                val view = View(context)
                view.isClickable = true
                view.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            // 사용자 터치 이벤트 발생 시 타이머 중지
                            Log.d("TimerWorker", "Action Down")
                            workManager?.cancelUniqueWork("TimerWorker")
                            windowManager.removeView(view)
                        }
                        else -> {}
                    }
                    false
                }

                windowManager.addView(view, params)
            }
            Intent.ACTION_SCREEN_OFF -> {
                scheduleWorker(context)
            }
        }
    }

    private fun scheduleWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val screenStateRequest = OneTimeWorkRequestBuilder<TimerWorker>()
            .setInitialDelay(24, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        workManager = WorkManager.getInstance(context)
        workManager?.enqueueUniqueWork(
            "TimerWorker",
            ExistingWorkPolicy.KEEP,
            screenStateRequest,
        )
    }
}
