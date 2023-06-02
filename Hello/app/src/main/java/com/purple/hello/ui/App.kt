package com.purple.hello.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.purple.hello.domain.account.CheckLoggedInUseCase
import com.purple.hello.loading.LoadingScreen
import com.purple.hello.login.LoginScreen
import com.purple.hello.navigation.HiNavHost

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HiApp(
    windowSizeClass: WindowSizeClass,
    checkLoggedInUseCase: CheckLoggedInUseCase,
    appState: AppState = rememberAppState(
        windowSizeClass = windowSizeClass,
        checkLoggedInUseCase = checkLoggedInUseCase,
    ).value,
) {
    Scaffold { padding ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            when (appState) {
                is AppState.Init -> {
                    LoadingScreen()
                }
                is AppState.LoggedIn -> {
                    HiNavHost(appState)
                }
                is AppState.LoggedOut -> {
                    LoginScreen()
                }
            }
        }
    }
}
