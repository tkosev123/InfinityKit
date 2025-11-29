package com.tk.infinitykit.presentation.features.login

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