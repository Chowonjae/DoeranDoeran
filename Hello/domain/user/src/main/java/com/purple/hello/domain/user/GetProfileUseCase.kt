package com.purple.hello.domain.user

import com.purple.hello.data.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import com.purple.core.model.Profile
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<Profile> = userRepository.getProfile()
}
