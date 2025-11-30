package com.tk.domain.usecase

import com.tk.domain.models.LoginError
import com.tk.domain.models.LoginResult
import com.tk.domain.repository.AuthenticationRepository
import com.tk.domain.utils.CredentialsValidator
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
    private val validator: CredentialsValidator
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        val validationError = validator.validate(email, password)

        if (validationError != null) {
            return LoginResult.Error(LoginError.Validation(validationError))
        }

        return repository.login(email, password)
    }

    companion object {
        private val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    }
}