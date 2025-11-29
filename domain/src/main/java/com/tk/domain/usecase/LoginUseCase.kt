package com.tk.domain.usecase

import com.tk.domain.models.AuthenticationError
import com.tk.domain.models.LoginResult
import com.tk.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        if (email.isBlank() && password.isBlank())
            return LoginResult.Error(AuthenticationError.Validation.EmptyEmailPassword)

        if (email.isBlank())
            return LoginResult.Error(AuthenticationError.Validation.EmptyEmail)

        if (password.isBlank())
            return LoginResult.Error(AuthenticationError.Validation.EmptyPassword)

        if (!EMAIL_REGEX.matches(email))
            return LoginResult.Error(AuthenticationError.Validation.InvalidEmail)

        if (password.length < 6)
            return LoginResult.Error(AuthenticationError.Validation.ShortPassword)

        return authenticationRepository.login(email, password)
    }

    companion object {
        private val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    }
}