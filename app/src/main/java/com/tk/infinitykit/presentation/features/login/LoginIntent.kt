package com.tk.infinitykit.presentation.features.login

sealed interface LoginIntent {
    data class Login(val email: String, val password: String) : LoginIntent
    data class EmailChanged(val email: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data object TogglePasswordVisibility: LoginIntent
}