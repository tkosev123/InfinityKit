package com.tk.domain.models

sealed interface RegisterResult {
    data class Success(val uid: String) : RegisterResult
    data class Error(val error: RegisterError) : RegisterResult
}

sealed interface RegisterError {
    data class Validation(val error: CredentialsValidationError) : RegisterError

    sealed interface Remote : RegisterError {
        object InvalidCredentials : Remote
        object WeakPassword : Remote
        object EmailAlreadyUsed : Remote
        data class Unknown(val message: String? = null) : Remote
    }
}