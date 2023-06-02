package com.purple.hello.feature.setting.app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.composable
import com.purple.hello.feature.setting.app.AppSettingRoute

const val appSettingNavigationRoute = "app_setting_route"

fun NavController.navigateToAppSetting(navOptions: NavOptions? = null) {
    this.navigate(appSettingNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.appSettingScreen(
    onBackClick: () -> Unit,
    clearNav: () -> Unit,
    onClickProfileSetting: () -> Unit,
) {
    composable(
        route = appSettingNavigationRoute,
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
        AppSettingRoute(
            onBackClick = onBackClick,
            clearNav = clearNav,
            onClickProfileSetting = onClickProfileSetting,
        )
    }
}
