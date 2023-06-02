package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.request.RoomJoinRequest
import com.purple.data.rooms.model.response.*
import retrofit2.Response

interface RoomDataSource {

    suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    ): Response<RoomCreationResponse>

    suspend fun joinRoom(roomJoinRequest: RoomJoinRequest): Response<RoomJoinResponse>

    suspend fun getRoomList(): Response<List<RoomListResponse>>

    suspend fun getMembersInRoom(
        roomId: Long,
    ): Response<MembersResponse>

    suspend fun getRoomCode(roomId: Long): Response<RoomCodeResponse>

    suspend fun getJoinInfo(roomId: Long): Response<RoomJoinInfoResponse>

    suspend fun getPasswordQuestion(roomId: Long): Response<RoomPasswordQuestionResponse>

    suspend fun updateRoomName(
        userRoomId: Long,
        roomName: String,
    ): Response<Void>

    suspend fun updateUserName(
        userRoomId: Long,
        userName: String,
    ): Response<Void>

    suspend fun updatePassword(
        roomId: Long,
        password: String,
        question: String,
    ): Response<Void>

//    suspend fun exitRoom(
//        userRoomId: Long,
//    ): Response<Void>

    suspend fun deleteRoom(
        roomId: Long,
    ): Response<Void>
}
