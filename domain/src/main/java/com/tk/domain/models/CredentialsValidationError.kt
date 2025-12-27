package com.tk.domain.models

sealed interface CredentialsValidationError {
    object EmptyEmail : CredentialsValidationError
    object InvalidEmail : CredentialsValidationError
    object EmptyPassword : CredentialsValidationError
    object ShortPassword : CredentialsValidationError
    object EmptyEmailPassword : CredentialsValidationError
}