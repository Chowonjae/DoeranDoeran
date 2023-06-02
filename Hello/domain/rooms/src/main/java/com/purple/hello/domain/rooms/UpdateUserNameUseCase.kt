package com.purple.hello.domain.rooms

import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class UpdateUserNameUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(userRoomId: Long, newUserName: String) =
        roomRepository.updateUserName(userRoomId, newUserName)
}
