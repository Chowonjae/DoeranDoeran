package com.purple.hello.domain.rooms

import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class GetRoomCodeUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(roomId: Long): String =
        roomRepository.getRoomCode(roomId)

}
