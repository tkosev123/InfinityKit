package com.tk.infinitykit.presentation.navigation.auth

sealed interface AuthEvent

sealed interface AuthIntent {
    data class Navigate(val key: AuthenticationScreen) : AuthIntent
    object Pop : AuthIntent
}

data class AuthState(
    val backStack: List<AuthenticationScreen> = listOf(AuthenticationScreen.LoginScreen)
)
