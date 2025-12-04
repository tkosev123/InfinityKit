package com.tk.infinitykit.presentation.features.register

sealed interface RegisterEvent {
    data class EmailChanged(val email: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
}

sealed interface RegisterIntent {
    data class Register(val email: String, val password: String) : RegisterIntent
    data class EmailChanged(val email: String) : RegisterIntent
    data class PasswordChanged(val password: String) : RegisterIntent
    data object TogglePasswordVisibility: RegisterIntent
}

data class RegisterState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = ""
)