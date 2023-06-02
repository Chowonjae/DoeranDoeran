package com.purple.data.rooms.datasource

import com.purple.data.rooms.model.request.*
import com.purple.data.rooms.model.response.*
import com.purple.data.rooms.service.RoomService
import retrofit2.Response
import javax.inject.Inject

class RemoteRoomDataSource @Inject constructor(
    private val roomService: RoomService,
) : RoomDataSource {

    override suspend fun createRoom(roomName: String, userName: String, roomQuestion: String, roomPassword: String) =
        roomService.createRoom(
            RoomCreationRequest(roomName, userName, roomQuestion, roomPassword),
        )

    override suspend fun joinRoom(roomJoinRequest: RoomJoinRequest) = roomService.joinRoom(roomJoinRequest)

    override suspend fun getRoomList(): Response<List<RoomListResponse>> =
        roomService.getRoomList()

    override suspend fun getMembersInRoom(roomId: Long): Response<MembersResponse> =
        roomService.getMembersInRoom(roomId)

    override suspend fun getRoomCode(roomId: Long): Response<RoomCodeResponse> =
        roomService.getRoomCode(roomId)

    override suspend fun getJoinInfo(roomId: Long): Response<RoomJoinInfoResponse> =
        roomService.getJoinInfo(roomId)

    override suspend fun getPasswordQuestion(roomId: Long): Response<RoomPasswordQuestionResponse> =
        roomService.getPasswordQuestion(roomId)

    override suspend fun updateRoomName(userRoomId: Long, roomName: String) =
        roomService.updateRoomName(
            RoomNameUpdateRequest(userRoomId, roomName),
        )

    override suspend fun updateUserName(userRoomId: Long, userName: String) =
        roomService.updateUserName(
            UserNameUpdateRequest(userRoomId, userName),
        )

    override suspend fun updatePassword(roomId: Long, password: String, question: String): Response<Void> =
        roomService.updatePassword(
            RoomPasswordUpdateRequest(roomId, password, question),
        )

    suspend fun exitRoom(userRoomId: Long) =
        roomService.exitRoom(userRoomId)

    override suspend fun deleteRoom(roomId: Long) =
        roomService.deleteRoom(
            DeleteRoomRequest(roomId),
        )
}
