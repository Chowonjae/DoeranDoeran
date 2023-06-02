package com.purple.hello

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.content.ContextCompat
import com.purple.core.designsystem.theme.HiTheme
import com.purple.hello.core.datastore.DeviceDataStore
import com.purple.hello.domain.account.CheckLoggedInUseCase
import com.purple.hello.stateReceiver.ScreenStateService
import com.purple.hello.ui.HiApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var checkLoggedInUseCase: CheckLoggedInUseCase

    @Inject
    lateinit var deviceDataStore: DeviceDataStore

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HiTheme {
                HiApp(
                    windowSizeClass = calculateWindowSizeClass(this),
                    checkLoggedInUseCase = checkLoggedInUseCase,
                )
            }
        }
        val firebaseNotification = HiFirebaseNotificationManager(activity = this, deviceDataStore)
        firebaseNotification.init()

        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + this.packageName)
            this.startActivity(intent)
        }
    }
}
