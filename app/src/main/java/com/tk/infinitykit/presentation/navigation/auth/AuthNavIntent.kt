package com.tk.infinitykit.presentation.navigation.auth

sealed interface AuthNavIntent {
    data class Navigate(val key: AuthenticationScreen) : AuthNavIntent
    object Pop : AuthNavIntent
}
