package com.purple.data.rooms.repository

import com.purple.core.model.JoinRoomInputValue
import com.purple.core.model.Room
import com.purple.core.model.RoomSettingOptions
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>
    fun getRoom(roomId: Long): Flow<Room>
    suspend fun getRoomCode(roomId: Long): String
    suspend fun getJoinRoomData(roomId: Long): JoinRoomInputValue
    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    ): Long
    suspend fun fetchMembersInRoom(
        roomId: Long,
    )
    suspend fun fetchRooms()
    suspend fun joinRoom(roomId: Long, joinRoomInputValue: JoinRoomInputValue): String
    fun getRoomSettings(roomId: Long): Flow<RoomSettingOptions>
    suspend fun updateUserName(userRoomId: Long, userName: String)
    suspend fun updateRoomName(userRoomId: Long, roomName: String)
    suspend fun updatePassword(roomId: Long, passwordQuestion: String, password: String)
    suspend fun exitRoom(userRoomId: Long)
    suspend fun deleteRoom(roomId: Long)
}
