package com.purple.hello.feature.setting.profile.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.purple.hello.feature.setting.profile.ProfileSettingRoute

const val profileSettingNavigationRoute = "profile_setting_route"

fun NavController.navigateToProfileSetting(navOptions: NavOptions? = null) {
    this.navigate(profileSettingNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.profileSettingScreen(
    onBackClick: () -> Unit,
    onClickRooms: () -> Unit,
) {
    composable(
        route = profileSettingNavigationRoute,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500),
            )
        },
    ) {
        ProfileSettingRoute(
            onBackClick = onBackClick,
            onClickRooms = onClickRooms,
        )
    }
}
