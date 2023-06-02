package com.purple.core.database.dao

import androidx.room.*
import com.purple.core.database.entity.RoomEntity
import com.purple.core.database.model.RoomWithMembers
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRoom(room: RoomEntity)

    @Query("UPDATE rooms SET userRoomId = :userRoomId, roomName = :roomName WHERE roomId = :roomId")
    fun updateRoom(roomId: Long, userRoomId: Long?, roomName: String?): Int

    @Transaction
    fun upsertRoom(room: RoomEntity) {
        val updatedRows = updateRoom(room.roomId, room.userRoomId, room.roomName)
        if (updatedRows == 0) {
            insertRoom(room)
        }
    }

    @Query("UPDATE rooms SET roomName = :roomName WHERE userRoomId = :userRoomId")
    fun updateRoomName(userRoomId: Long, roomName: String)

    @Query(
        "UPDATE user_room_cross SET nickName = :userName WHERE roomId = (SELECT roomId FROM rooms WHERE userRoomId = :userRoomId)", // ktlint-disable max-line-length
    )
    fun updateUserName(userRoomId: Long, userName: String)

    @Query("UPDATE room_common_option SET passwordQuestion = :question, password = :password WHERE roomId = :roomId")
    fun updatePassword(roomId: Long, password: String, question: String)

    @Query("DELETE FROM rooms WHERE userRoomId = :userRoomId")
    fun exitRoom(userRoomId: Long)

    @Query("DELETE FROM rooms WHERE roomId = :roomId")
    fun deleteRoom(roomId: Long)

    @Transaction
    @Query("SELECT * FROM rooms ORDER BY recentVisitedTime DESC")
    fun getRoomsWithMembers(): Flow<List<RoomWithMembers>>

    @Transaction
    @Query("SELECT * FROM rooms WHERE roomId = :roomId")
    fun getRoomWithMembers(roomId: Long): Flow<RoomWithMembers>

    @Query("SELECT * FROM rooms WHERE roomId = :roomId")
    fun getRoom(roomId: Long): RoomEntity
}
