package com.tk.domain.utils


import com.tk.domain.models.CredentialsValidationError
import javax.inject.Inject

class CredentialsValidator @Inject constructor() {

    fun validate(email: String, password: String): CredentialsValidationError? {

        if (email.isBlank() && password.isBlank())
            return CredentialsValidationError.EmptyEmailPassword

        if (email.isBlank())
            return CredentialsValidationError.EmptyEmail

        if (!EMAIL_REGEX.matches(email))
            return CredentialsValidationError.InvalidEmail

        if (password.isBlank())
            return CredentialsValidationError.EmptyPassword

        if (password.length < 6)
            return CredentialsValidationError.ShortPassword

        return null
    }

    companion object {
        private val EMAIL_REGEX =
            Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    }
}