package com.tk.domain.models

sealed interface LoginResult {
    object  Success : LoginResult
    data class Error(val error: LoginError) : LoginResult
}

sealed interface LoginError {
    data class Validation(val error: CredentialsValidationError) : LoginError

    sealed interface Remote : LoginError {
        object InvalidCredentials : Remote
        object UserNotFound : Remote
    }

    data class Unknown(val message: String? = null) : LoginError
}