package com.tk.infinitykit.presentation.navigation.main

import com.tk.infinitykit.presentation.components.BottomNavItem
import com.tk.infinitykit.presentation.components.ChatNavItem
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
    val tabStacks: Map<BottomNavItem, List<AppRoute>> = mapOf(
        DashboardNavItem to listOf(AppRoute.Dashboard),
        ChatNavItem to listOf(AppRoute.ConversationListPreview)
    ),
    val combinedBackStack: List<AppRoute> = listOf(AppRoute.Dashboard)
)