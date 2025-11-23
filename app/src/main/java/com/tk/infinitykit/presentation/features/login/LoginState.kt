package com.tk.infinitykit.presentation.features.login

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val error: String? = null
)