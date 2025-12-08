package com.tk.infinitykit.presentation.navigation.main

import androidx.compose.runtime.mutableStateListOf
import com.tk.infinitykit.presentation.components.bottomnavigation.BottomNavItem
import com.tk.infinitykit.presentation.components.bottomnavigation.ChatNavItem
import com.tk.infinitykit.presentation.components.bottomnavigation.DashboardNavItem
import com.tk.mvi.BaseViewModel

class MainViewModel :
    BaseViewModel<MainState<AppRoute, BottomNavItem>, MainEvent, MainIntent>(
        MainState(
            tabBackStacks = hashMapOf(
                DashboardNavItem to mutableStateListOf(AppRoute.Dashboard),
                ChatNavItem to mutableStateListOf(AppRoute.ConversationListPreview)
            ),
            currentTab = DashboardNavItem,
            rootTab = DashboardNavItem,
            combinedBackStack = mutableStateListOf(AppRoute.Dashboard)
        )
    ) {

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.Push -> push(intent.root, intent.route)
            is MainIntent.Pop -> pop()
            is MainIntent.SwitchRoot -> switchRoot(intent.root)
        }
    }

    private fun switchRoot(root: BottomNavItem) {
        updateState { copy(currentTab = root) }
        updateCombinedBackStack()
    }

    private fun pop() {
        val currentTab = state.value.currentTab
        updateState {
            val newStack = state.value.tabBackStacks.toMutableMap()
            val currentStack = newStack[currentTab] ?: return@updateState(this)

            if(currentStack.size > 1) {
                currentStack.removeLastOrNull()
                newStack[currentTab] = currentStack
                return@updateState(copy(tabBackStacks = newStack))
            }

            if(currentTab != rootTab) {
                return@updateState(copy(currentTab = rootTab))
            }
            this
        }
        updateCombinedBackStack()
    }

    private fun push(root: BottomNavItem, route: AppRoute) {
        state.value.tabBackStacks.getOrPut(root) { mutableStateListOf() }.add(route)
        updateCombinedBackStack()
    }

    private fun updateCombinedBackStack() {
        val currentStack = state.value.tabBackStacks[state.value.currentTab] ?: return
        if(state.value.currentTab == state.value.rootTab) {
            state.value.combinedBackStack.clear()
            state.value.combinedBackStack.addAll(currentStack)
        }
        else {
            val firstBackStack = state.value.tabBackStacks[state.value.rootTab] ?: return
            state.value.combinedBackStack.clear()
            state.value.combinedBackStack.addAll(firstBackStack + currentStack)
        }
    }
}