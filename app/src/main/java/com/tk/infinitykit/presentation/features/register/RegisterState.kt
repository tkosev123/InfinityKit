package com.tk.infinitykit.presentation.features.register

data class RegisterState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = ""
)