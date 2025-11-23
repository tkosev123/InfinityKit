package com.tk.infinitykit.presentation.features.login

sealed interface LoginEvent {
    object NavigateHome : LoginEvent
    data class ShowError(val message: String) : LoginEvent
}