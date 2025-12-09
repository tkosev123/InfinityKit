package com.tk.infinitykit.presentation.navigation.auth

sealed interface AuthEvent

sealed interface AuthIntent {
    data class Push(val key: AuthRoute) : AuthIntent
    object Pop : AuthIntent
}

data class AuthState(
    val backStack: List<AuthRoute> = listOf(AuthRoute.LoginScreen)
)
