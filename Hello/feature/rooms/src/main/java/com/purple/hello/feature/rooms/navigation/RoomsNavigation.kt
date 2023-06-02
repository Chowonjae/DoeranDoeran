package com.purple.hello.feature.rooms.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.purple.hello.feature.rooms.CameraScreen
import com.purple.hello.feature.rooms.JoinRoomDialog
import com.purple.hello.feature.rooms.RoomDetailRoute
import com.purple.hello.feature.rooms.RoomsRoute

internal const val roomIdArg = "roomIdArg"
internal const val userIdArg = "userIdArg"
internal const val joinIdArg = "joinIdArg"

const val roomsNavigationRoute = "rooms_route"
const val roomsListRoute = "room_list_route"
const val roomDetailRoute = "room_detail_route"
const val cameraRoute = "camera_route"

fun NavController.navigateToRooms(navOptions: NavOptions? = null) {
    this.navigate(roomsListRoute, navOptions)
}

fun NavController.navigateRoomDetail(roomId: Long, navOptions: NavOptions? = null) {
    this.navigate("$roomDetailRoute/$roomId", navOptions)
}

fun NavController.navigateToCamera(roomId: Long, navOptions: NavOptions? = null) {
    this.navigate("$cameraRoute/$roomId", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.R)
fun NavGraphBuilder.roomsGraph(
    navController: NavController,
    onBackClick: () -> Unit,
    onClickAppSetting: () -> Unit,
    onClickRoomSetting: (roomId: Long) -> Unit,
    onClickNotification: () -> Unit,
) {
    navigation(
        startDestination = roomsListRoute,
        route = roomsNavigationRoute,
    ) {
        composable(
            route = roomsListRoute,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://doeran.kr/room/{joinRoomId}"
                },
            ),
            arguments = listOf(
                navArgument("joinRoomId") {
                    type = NavType.LongType
                    defaultValue = 0
                },
            ),
            enterTransition = { slideInHorizontally(animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(500)) },
        ) {
            val joinRoomId = it.arguments?.getLong("joinRoomId") ?: 0L
            val showDialog = remember(joinRoomId) { mutableStateOf(joinRoomId != 0L) }

            if (joinRoomId != 0L) {
                it.arguments!!.remove("joinRoomId")
                if (showDialog.value) {
                    JoinRoomDialog(
                        onDismiss = { showDialog.value = false },
                        joinRoomId = joinRoomId,
                        onConfirm = {
                            navController.navigateRoomDetail(joinRoomId)
                        },
                    )
                }
            }
            RoomsRoute(
                onClickRoom = {
                    navController.navigateRoomDetail(it)
                },
                onClickAppSetting = {
                    onClickAppSetting()
                },
                onClickNotification = {
                    onClickNotification()
                },
            )
        }
        composable(
            route = "$roomDetailRoute/{$roomIdArg}",
            arguments = listOf(
                navArgument(roomIdArg) { type = NavType.LongType },
            ),
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
            RoomDetailRoute(
                onClickCameraButton = { roomId ->
                    navController.navigateToCamera(roomId)
                },
                onBackClick = {
                    onBackClick()
                },
                onClickRoomSetting = {
                    onClickRoomSetting(it)
                },
            )
        }
        composable(
            route = "$cameraRoute/{$roomIdArg}",
            arguments = listOf(
                navArgument(roomIdArg) { type = NavType.LongType },
            ),
        ) {
            CameraScreen(
                backToDetail = { navController.popBackStack() },
            )
        }
    }
}
