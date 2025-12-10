package com.tk.domain.usecase

import com.tk.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    operator fun invoke() {
        authenticationRepository.logout()
    }
}