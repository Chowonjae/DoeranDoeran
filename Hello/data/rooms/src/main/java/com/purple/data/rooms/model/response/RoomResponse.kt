package com.purple.data.rooms.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomCreationResponse(
    @SerialName("createAt")
    val createAt: String,
    @SerialName("dayAlarm")
    val dayAlarm: String,
    @SerialName("moveAlarm")
    val moveAlarm: String,
    @SerialName("roomId")
    val roomId: Long,
    @SerialName("roomName")
    val roomName: String,
    @SerialName("safeAlarm")
    val safeAlarm: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userRoomId")
    val userRoomId: Long,
    @SerialName("userRoomRole")
    val userRoomRole: String,
)

@Serializable
data class RoomJoinResponse(
    @SerialName("createAt")
    val createAt: String,
    @SerialName("dayAlarm")
    val dayAlarm: String,
    @SerialName("moveAlarm")
    val moveAlarm: String,
    @SerialName("roomName")
    val roomName: String,
    @SerialName("safeAlarm")
    val safeAlarm: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userRoomId")
    val userRoomId: Long,
    @SerialName("userRoomRole")
    val userRoomRole: String,
)

