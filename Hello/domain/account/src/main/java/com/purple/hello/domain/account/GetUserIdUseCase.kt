package com.purple.hello.domain.account

import com.purple.hello.data.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<Long> = userRepository.getUserId()
}
