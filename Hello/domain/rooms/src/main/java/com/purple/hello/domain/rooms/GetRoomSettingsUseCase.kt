package com.purple.hello.domain.rooms

import com.purple.core.model.RoomSettingOptions
import com.purple.data.rooms.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoomSettingsUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke(roomId: Long): Flow<RoomSettingOptions> =
        roomRepository.getRoomSettings(roomId)
}
