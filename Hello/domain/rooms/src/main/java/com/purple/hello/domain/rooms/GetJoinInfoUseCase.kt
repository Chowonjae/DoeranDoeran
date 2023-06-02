package com.purple.hello.domain.rooms

import com.purple.core.model.InputData
import com.purple.core.model.JoinRoomInputValue
import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class GetJoinInfoUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(roomId: Long): JoinRoomInputValue =
        roomRepository.getJoinRoomData(roomId)
}
