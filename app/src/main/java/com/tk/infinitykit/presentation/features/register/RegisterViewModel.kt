package com.tk.infinitykit.presentation.features.register

import com.tk.mvi.BaseViewModel

class RegisterViewModel: BaseViewModel<RegisterState, RegisterEvent, RegisterIntent> (RegisterState())  {
    override suspend fun handleIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.EmailChanged -> {
                updateState { copy(email = intent.email) }
            }
            is RegisterIntent.PasswordChanged -> {
                updateState { copy(password = intent.password) }
            }

            is RegisterIntent.Register -> TODO()
            RegisterIntent.TogglePasswordVisibility -> TODO()
        }
    }

}