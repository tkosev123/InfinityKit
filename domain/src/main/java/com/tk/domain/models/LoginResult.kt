package com.tk.domain.models

sealed interface LoginResult {
    object  Success : LoginResult
    data class Error(val error: AuthenticationError) : LoginResult
}

sealed interface AuthenticationError {
    sealed interface Validation : AuthenticationError {
        object EmptyEmail : Validation
        object InvalidEmail : Validation
        object EmptyPassword : Validation
        object ShortPassword : Validation

        object EmptyEmailPassword: Validation
    }

    sealed interface Remote : AuthenticationError {
        object InvalidCredentials : Remote
        object UserNotFound : Remote
    }

    data class Unknown(val message: String? = null) : AuthenticationError
}