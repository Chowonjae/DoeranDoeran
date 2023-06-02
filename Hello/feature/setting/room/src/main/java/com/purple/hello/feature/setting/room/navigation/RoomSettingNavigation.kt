package com.purple.hello.feature.setting.room.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.purple.hello.feature.setting.room.RoomSettingRoute

internal const val roomIdArg = "roomId"
const val roomSettingNavigationRoute = "room_setting_route"

fun NavController.navigateToRoomSetting(navOptions: NavOptions? = null, roomId: Long) {
    this.navigate("$roomSettingNavigationRoute/$roomId", navOptions)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.roomSettingScreen(
    onBackClick: () -> Unit,
    onClickRooms: () -> Unit,
) {
    composable(
        route = "$roomSettingNavigationRoute/{$roomIdArg}",
        arguments = listOf(
            navArgument(roomIdArg) { type = NavType.LongType },
        ),
    ) {
        RoomSettingRoute(
            onBackClick = onBackClick,
            onClickRooms = onClickRooms,
        )
    }
}
