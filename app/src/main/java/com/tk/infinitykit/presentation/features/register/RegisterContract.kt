package com.tk.infinitykit.presentation.features.register

sealed interface RegisterEvent {
    data class ShowError(val message: String) : RegisterEvent
}

sealed interface RegisterIntent {
    data class Register(val email: String, val password: String, val firstName: String, val lastName: String) : RegisterIntent
    data class EmailChanged(val email: String) : RegisterIntent
    data class PasswordChanged(val password: String) : RegisterIntent
    data class FirstNameChanged(val firstName: String) : RegisterIntent
    data class LastNameChanged(val lastName: String) : RegisterIntent
    data object TogglePasswordVisibility: RegisterIntent
}

data class RegisterState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val isEmailError: Boolean = false,
    val emailErrorText: String = "",
    val isPasswordError: Boolean = false,
    val passwordErrorText: String = "",
    val isFirstNameError: Boolean = false,
    val firstNameErrorText: String = "",
    val isLastNameError: Boolean = false,
    val lastNameErrorText: String = "",
    val isPasswordVisible: Boolean = false
)