package com.tk.infinitykit.presentation.features.login

sealed interface LoginEvent {
    data class ShowError(val message: String) : LoginEvent
}

sealed interface LoginIntent {
    data class Login(val email: String, val password: String) : LoginIntent
    data class EmailChanged(val email: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data object TogglePasswordVisibility: LoginIntent
}

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isEmailError: Boolean = false,
    val emailErrorText: String = "",
    val isPasswordError: Boolean = false,
    val passwordErrorText: String = "",
    val isPasswordVisible: Boolean = false
)