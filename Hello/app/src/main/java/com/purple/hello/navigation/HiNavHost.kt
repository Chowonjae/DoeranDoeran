package com.purple.hello.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.purple.hello.feature.rooms.navigation.roomsGraph
import com.purple.hello.feature.rooms.navigation.roomsNavigationRoute
import com.purple.hello.ui.AppState

@Composable
fun HiNavHost(
    appState: AppState.LoggedIn,
    modifier: Modifier = Modifier,
    startDestination: String = roomsNavigationRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        roomsGraph(navController)
    }
}