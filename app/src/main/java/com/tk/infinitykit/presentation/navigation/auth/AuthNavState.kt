package com.tk.infinitykit.presentation.navigation.auth

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class AuthNavState(
    val backStack: SnapshotStateList<AuthenticationScreen> = mutableStateListOf()
)
