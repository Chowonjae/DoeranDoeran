package com.purple.hello.domain.rooms

import com.purple.data.rooms.repository.RoomRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend operator fun invoke(roomId: Long, newPasswordQuestion: String, newPassword: String) =
        roomRepository.updatePassword(roomId, newPasswordQuestion, newPassword)
}
