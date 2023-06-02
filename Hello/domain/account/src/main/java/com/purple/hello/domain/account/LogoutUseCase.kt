package com.purple.hello.domain.account

import com.purple.hello.data.account.AccountRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend fun logout() =
        accountRepository.logout()
}
