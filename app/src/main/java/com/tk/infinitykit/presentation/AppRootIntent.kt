package com.tk.infinitykit.presentation

sealed interface AppRootIntent {
    object ObserveAuthState: AppRootIntent
}