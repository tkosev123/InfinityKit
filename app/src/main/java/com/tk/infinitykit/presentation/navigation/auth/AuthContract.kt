package com.tk.infinitykit.presentation.navigation.auth

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

sealed interface AuthEvent

sealed interface AuthIntent {
    data class Navigate(val key: AuthenticationScreen) : AuthIntent
    object Pop : AuthIntent
}

data class AuthState(
    val backStack: SnapshotStateList<AuthenticationScreen> = mutableStateListOf()
)
