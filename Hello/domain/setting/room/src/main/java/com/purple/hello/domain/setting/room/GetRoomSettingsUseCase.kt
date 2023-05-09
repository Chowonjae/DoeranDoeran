package com.purple.hello.domain.setting.room

import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class GetRoomSettingsUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(roomId: Long) =
        roomRepository.getRoomSettings(roomId)
}
