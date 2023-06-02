package com.purple.hello.domain.account

import com.purple.hello.data.account.AccountRepository
import com.purple.hello.data.user.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
) {
    suspend fun loginWithGoogle(idToken: String) {
        accountRepository.loginWithGoogle(idToken)
        userRepository.fetchUserInfo()
    }

    suspend fun loginWithKakao(accessToken: String) {
        accountRepository.loginWithKakao(accessToken)
        userRepository.fetchUserInfo()
    }
}
