package com.tk.infinitykit.presentation.navigation.inapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation.BottomNavItem

sealed interface MainAppIntent {
    data class Push(val root: BottomNavItem, val destination: AppDestination) : MainAppIntent
    object  Pop : MainAppIntent
    data class SwitchRoot(val root: BottomNavItem) : MainAppIntent
}

sealed interface MainAppEvent

data class MainAppState<T, R>(
    val tabBackStacks: MutableMap<R, SnapshotStateList<T> > = mutableMapOf(),
    val combinedBackStack: SnapshotStateList<T> = mutableStateListOf(),
    var currentTab: R,
    var rootTab: R
)