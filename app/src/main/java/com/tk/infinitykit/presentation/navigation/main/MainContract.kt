package com.tk.infinitykit.presentation.navigation.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.tk.infinitykit.presentation.components.BottomNavItem
import com.tk.infinitykit.presentation.components.DashboardNavItem
import com.tk.infinitykit.presentation.components.FabMenuItem

sealed interface MainIntent {
    data class Push(val root: BottomNavItem, val route: AppRoute) : MainIntent
    object Pop : MainIntent
    data class SwitchRoot(val root: BottomNavItem) : MainIntent
    object ToggleFab : MainIntent
    data class FabMenuItemClick(val type: FabMenuItem.FabType) : MainIntent
}

sealed interface MainEvent

data class MainState(
    val backStackConfig: BackStackConfig = BackStackConfig(),
    val isFabMenuExpanded: Boolean = false,
    val isBottomNavVisible: Boolean = true
)

data class BackStackConfig(
    val rootTab: BottomNavItem = DashboardNavItem,
    val currentTab: BottomNavItem = DashboardNavItem,
    val tabBackStacks: MutableMap<BottomNavItem, SnapshotStateList<AppRoute>> = mutableMapOf(),
    val combinedBackStack: SnapshotStateList<AppRoute> = mutableStateListOf()
)