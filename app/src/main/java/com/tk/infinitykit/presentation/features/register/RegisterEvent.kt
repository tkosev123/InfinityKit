package com.tk.infinitykit.presentation.features.register

sealed interface RegisterEvent {
    data class EmailChanged(val email: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
}