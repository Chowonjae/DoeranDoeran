package com.purple.hello.domain.setting.room

import com.purple.data.rooms.RoomRepository
import javax.inject.Inject

class UpdateRoomNameUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(userRoomId: Long, newRoomName: String) =
        roomRepository.updateRoomName(userRoomId, newRoomName)
}