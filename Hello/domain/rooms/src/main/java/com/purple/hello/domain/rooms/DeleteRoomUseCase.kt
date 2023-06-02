package com.purple.hello.domain.rooms

import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class DeleteRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(roomId: Long) =
        roomRepository.deleteRoom(roomId)
}
