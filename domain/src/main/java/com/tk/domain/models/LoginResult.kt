package com.tk.domain.models

sealed class LoginResult {
    object  Success : LoginResult()
    data class Error(val error: AuthenticationError) : LoginResult()
}

sealed class AuthenticationError {
    object InvalidCredentials : AuthenticationError()
    object UserNotFound : AuthenticationError()
    data class Unknown(val message: String?) : AuthenticationError()
}