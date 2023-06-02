package com.purple.hello.domain.rooms

import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class UpdateRoomNameUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(userRoomId: Long, newRoomName: String) =
        roomRepository.updateRoomName(userRoomId, newRoomName)
}
