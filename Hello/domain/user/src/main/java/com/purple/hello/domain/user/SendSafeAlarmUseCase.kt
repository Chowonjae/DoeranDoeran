package com.purple.hello.domain.user

import com.purple.hello.data.user.repository.UserRepository
import javax.inject.Inject

class SendSafeAlarmUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.sendSafeAlarm()
}
