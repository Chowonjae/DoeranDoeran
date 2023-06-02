package com.purple.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.purple.core.designsystem.R

object HiIcons {

    val AddRoom = Icons.Rounded.Add
    val ArrowBack = Icons.Rounded.ArrowBack
    val Notifications = Icons.Rounded.Notifications
    val MoreVert = Icons.Rounded.MoreVert
    val Settings = Icons.Rounded.Settings
    val ArrowRight = Icons.Rounded.KeyboardArrowRight
    val Calendar = Icons.Rounded.DateRange
    val PersonAdd = R.drawable.baseline_person_add_24
    val ArrowDropDown = Icons.Rounded.ArrowDropDown
}

sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}
