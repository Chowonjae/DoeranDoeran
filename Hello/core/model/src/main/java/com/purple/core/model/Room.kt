package com.purple.core.model

data class Room(
    val roomId: Long,
    val roomName: String,
    val members: List<Member>,
)

data class RoomSettingOptions(
    val roomName: String,
    val userRoomId: Long,
    val role: String,
    val userName: String,
    val passwordQuestion: String,
)
