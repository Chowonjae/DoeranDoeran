package com.purple.hello.domain.rooms

import com.purple.core.model.Room
import com.purple.data.rooms.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoomListUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke(): Flow<List<Room>> = roomRepository.getRooms()
}
