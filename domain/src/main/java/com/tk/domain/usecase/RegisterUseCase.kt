package com.tk.domain.usecase

import com.tk.domain.models.RegisterError
import com.tk.domain.models.RegisterResult
import com.tk.domain.repository.AuthenticationRepository
import com.tk.domain.utils.CredentialsValidator
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
    private val validator: CredentialsValidator
) {
    suspend operator fun invoke(email: String, password: String): RegisterResult {
        val validationError = validator.validate(email, password)

        if (validationError != null) {
            return RegisterResult.Error(RegisterError.Validation(validationError))
        }

        return repository.register(email, password)
    }
}