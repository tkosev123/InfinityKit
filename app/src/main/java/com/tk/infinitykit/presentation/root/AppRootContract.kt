package com.tk.infinitykit.presentation.root

interface AppRootEvent

sealed interface AppRootIntent {
    object ObserveAuthState: AppRootIntent
    object Pop: AppRootIntent
}

data class AppRootState(
    val isLoading: Boolean = true,
    val backStack: List<Route> = listOf(Route.Auth)
)