package com.tk.infinitykit.presentation.features.register

sealed interface RegisterIntent {
    data class Register(val email: String, val password: String) : RegisterIntent
    data class EmailChanged(val email: String) : RegisterIntent
    data class PasswordChanged(val password: String) : RegisterIntent
    data object TogglePasswordVisibility: RegisterIntent
}