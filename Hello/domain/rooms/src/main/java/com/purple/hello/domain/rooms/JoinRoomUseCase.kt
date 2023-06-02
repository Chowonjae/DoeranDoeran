package com.purple.hello.domain.rooms

import com.purple.core.model.JoinRoomInputValue
import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class JoinRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(roomId: Long, joinRoomData: JoinRoomInputValue): String =
        roomRepository.joinRoom(roomId, joinRoomData)
}
