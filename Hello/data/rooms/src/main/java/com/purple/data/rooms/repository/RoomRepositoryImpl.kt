package com.purple.data.rooms.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.purple.core.database.dao.RoomDao
import com.purple.core.database.dao.UserDao
import com.purple.core.database.entity.RoomEntity
import com.purple.core.database.model.RoomWithMembers
import com.purple.core.database.model.asExternalModel
import com.purple.core.model.InputData
import com.purple.core.model.JoinRoomInputValue
import com.purple.core.model.Room
import com.purple.core.model.createInputDataByInputType
import com.purple.core.model.type.InputDialogType
import com.purple.data.rooms.datasource.RemoteRoomDataSource
import com.purple.data.rooms.model.request.RoomJoinRequest
import com.purple.data.rooms.model.response.RoomJoinInfoResponse
import com.purple.data.rooms.model.response.asMemberEntity
import com.purple.data.rooms.model.response.asMemberRoomEntity
import com.purple.data.rooms.model.response.asRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao,
    private val userDao: UserDao,
    private val remoteRoomDataSource: RemoteRoomDataSource,
) : RoomRepository {
    override fun getRooms(): Flow<List<Room>> =
        roomDao.getRoomsWithMembers().map { it.map(RoomWithMembers::asExternalModel) }

    override fun getRoom(roomId: Long): Flow<Room> =
        roomDao.getRoomWithMembers(roomId).map(RoomWithMembers::asExternalModel)

    override suspend fun getRoomCode(roomId: Long): String {
        runCatching {
            remoteRoomDataSource.getRoomCode(roomId)
        }.onFailure {
            throw it
        }.getOrThrow().let {
            return it.body()?.roomCode ?: ""
        }
    }

    override suspend fun getJoinRoomData(roomId: Long): JoinRoomInputValue {
        runCatching {
            remoteRoomDataSource.getJoinInfo(roomId)
        }.onFailure {
            Log.e("RoomRepository", it.stackTraceToString())
        }.getOrNull()?.body().let {
            return JoinRoomInputValue(
                roomName = createInputDataByInputType(InputDialogType.ROOM_NAME, it?.roomName ?: ""),
                nickName = createInputDataByInputType(InputDialogType.NAME, ""),
                password = InputData(
                    question = it?.roomQuestion ?: "",
                    placeHolder = "",
                    supportingText = "비밀번호를 입력해주세요",
                    inputValue = "",
                )
            )
        }
    }

    override suspend fun createRoom(
        roomName: String,
        userName: String,
        roomQuestion: String,
        roomPassword: String,
    ): Long {
        runCatching {
            remoteRoomDataSource.createRoom(roomName, userName, roomQuestion, roomPassword)
        }.onFailure { e ->
            throw e
        }.getOrThrow().let {
            val response = checkNotNull(it.body())
            roomDao.insertRoom(
                RoomEntity(
                    userRoomId = response.userRoomId,
                    roomId = response.roomId,
                    roomName = response.roomName,
                    recentVisitedTime = System.currentTimeMillis(),
                ),
            )
            return response.roomId
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchMembersInRoom(roomId: Long) {
        val response = remoteRoomDataSource.getMembersInRoom(roomId)

        if (response.isSuccessful) {
            val membersInRoom = response.body()
            membersInRoom?.members?.map {
                userDao.upsertMember(it.asMemberEntity())
                userDao.upsertMemberRoom(it.asMemberRoomEntity(roomId))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchRooms() {
        runCatching {
            remoteRoomDataSource.getRoomList()
        }.onFailure { e ->
            throw e
        }.getOrThrow().let {
            val roomList = checkNotNull(it.body())

            roomList.map { room ->
                roomDao.upsertRoom(room.asRoomEntity())
                room.members.map { member ->
                    userDao.upsertMember(member.asMemberEntity())
                    userDao.upsertMemberRoom(member.asMemberRoomEntity(room.roomId))
                }
            }
        }
    }

    override suspend fun joinRoom(roomId: Long, joinRoomInputValue: JoinRoomInputValue): String {
        return runCatching {
            remoteRoomDataSource.joinRoom(
                RoomJoinRequest(
                    roomId = roomId,
                    roomName = joinRoomInputValue.roomName.inputValue,
                    roomPassword = joinRoomInputValue.password.inputValue,
                    userName = joinRoomInputValue.nickName.inputValue
                )
            )
        }
        .onFailure {

        }.getOrThrow().let {
            if(it.isSuccessful) {
                val response = checkNotNull(it.body())

                roomDao.insertRoom(
                    RoomEntity(
                        roomId = roomId,
                        userRoomId = response.userRoomId,
                        roomName = response.roomName,
                        recentVisitedTime = System.currentTimeMillis()
                    )
                )

                "Success"
            }else {
                when(it.code()) {
                    400 -> "Password Error"
                    else -> "Error"
                }
            }
        }
    }

    override suspend fun getRoomSettings(roomId: Long) {
//        TODO
    }

//    override suspend fun getRoomSettings(roomId: Long): RoomSetting {
//        val room = roomDao.getRoom(roomId)
//        val user = userDao.getUserInRoom(userId, roomId)
//
//        return RoomSetting(
//            room.roomName,
//            room.userRoomId,
//
//        )
//    }

    override suspend fun updateRoomName(userRoomId: Long, roomName: String) {
        remoteRoomDataSource.updateRoomName(
            userRoomId,
            roomName,
        ).let {
            if (it.isSuccessful) {
                roomDao.updateRoomName(
                    userRoomId = userRoomId,
                    roomName = roomName,
                )
            }
        }
    }

    override suspend fun updateUserName(userRoomId: Long, userName: String) {
        remoteRoomDataSource.updateUserName(
            userRoomId,
            userName,
        ).let {
            if (it.isSuccessful) {
                roomDao.updateUserName(
                    userRoomId = userRoomId,
                    userName = userName,
                )
            }
        }
    }

    override suspend fun updatePassword(roomId: Long, passwordQuestion: String, password: String) {
        remoteRoomDataSource.updatePassword(
            roomId,
            password,
            passwordQuestion,
        ).let {
            if (it.isSuccessful) {
                roomDao.updatePassword(
                    roomId = roomId,
                    password = password,
                    question = passwordQuestion,
                )
            }
        }
    }

    override suspend fun leaveRoom(userRoomId: Long) {
        remoteRoomDataSource.exitRoom(
            userRoomId,
        ).let {
            if (it.isSuccessful) {
                roomDao.exitRoom(
                    userRoomId = userRoomId,
                )
            }
        }
    }

    override suspend fun deleteRoom(roomId: Long) {
        remoteRoomDataSource.deleteRoom(
            roomId,
        ).let {
            if (it.isSuccessful) {
                roomDao.deleteRoom(
                    roomId = roomId,
                )
            }
        }
    }
}