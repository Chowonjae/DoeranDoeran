package com.purple.data.rooms.service

import com.purple.data.rooms.model.request.*
import com.purple.data.rooms.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface RoomService {

    @GET("user/rooms")
    suspend fun getRoomList(): Response<List<RoomListResponse>>

    @POST("room/create")
    suspend fun createRoom(
        @Body requestForCreate: RoomCreationRequest,
    ): Response<RoomCreationResponse>

    @POST("room/join")
    suspend fun joinRoom(
        @Body requestForJoin: RoomJoinRequest,
    ): Response<RoomJoinResponse>

    @GET("room/user-list")
    suspend fun getMembersInRoom(
        @Query("roomId") roomId: Long,
    ): Response<MembersResponse>

    @GET("room/code")
    suspend fun getRoomCode(
        @Query("roomId") roomId: Long,
    ): Response<RoomCodeResponse>

    @GET("room/join-info")
    suspend fun getJoinInfo(
        @Query("roomId") roomId: Long,
    ): Response<RoomJoinInfoResponse>

    @GET("room/room-question")
    suspend fun getPasswordQuestion(
        @Query("roomId") roomId: Long,
    ): Response<RoomPasswordQuestionResponse>

    @PUT("room/name")
    suspend fun updateRoomName(
        @Body requestForUpdateRoomName: RoomNameUpdateRequest,
    ): Response<Void>

    @PUT("room/user")
    suspend fun updateUserName(
        @Body requestForUpdateUserName: UserNameUpdateRequest,
    ): Response<Void>

    @PUT("room/password")
    suspend fun updatePassword(
        @Body requestForUpdatePassword: RoomPasswordUpdateRequest,
    ): Response<Void>

    @PUT("room/exit")
    suspend fun exitRoom(
        @Query("userRoomId") userRoomId: Long,
    ): Response<Void>

    @HTTP(method = "DELETE", path = "room", hasBody = true)
    suspend fun deleteRoom(
        @Body requestForDeleteRoom: DeleteRoomRequest,
    ): Response<Void>
}
