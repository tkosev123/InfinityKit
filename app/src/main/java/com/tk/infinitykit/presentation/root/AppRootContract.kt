package com.tk.infinitykit.presentation.root

interface AppRootEvent

sealed interface AppRootIntent {
    object ObserveAuthState: AppRootIntent
}

data class AppRootState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true
)