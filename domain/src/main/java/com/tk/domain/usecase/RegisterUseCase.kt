package com.tk.domain.usecase

import com.tk.domain.models.RegisterError
import com.tk.domain.models.RegisterResult
import com.tk.domain.repository.AuthenticationRepository
import com.tk.domain.repository.UserRepository
import com.tk.domain.utils.CredentialsValidator
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val validator: CredentialsValidator
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): RegisterResult {
        val validationError = validator.validate(email, password)

        if (validationError != null) {
            return RegisterResult.Error(RegisterError.Validation(validationError))
        }

        return when (val registerResult = repository.register(email, password)) {
            is RegisterResult.Success -> try {
                userRepository.createUserInDatabase(registerResult.uid, email, firstName, lastName)
                registerResult
            } catch (e: Exception) {
                RegisterResult.Error(RegisterError.Remote.Unknown(e.message))
            }
            is RegisterResult.Error -> registerResult
        }
    }
}