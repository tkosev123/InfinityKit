package com.tk.domain.utils

import com.tk.domain.models.CredentialsValidationError
import javax.inject.Inject

class CredentialsValidator @Inject constructor() {
    fun validate(email: String, password: String): CredentialsValidationError? =
        when {
            email.isBlank() && password.isBlank() -> CredentialsValidationError.EmptyEmailPassword
            email.isBlank() -> CredentialsValidationError.EmptyEmail
            !EMAIL_REGEX.matches(email) -> CredentialsValidationError.InvalidEmail
            password.isBlank() -> CredentialsValidationError.EmptyPassword
            password.length < 6 -> CredentialsValidationError.ShortPassword
            else -> null
        }

    companion object {
        private val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    }
}