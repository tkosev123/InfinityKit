package com.tk.infinitykit.presentation.navigation.inapp

import androidx.compose.runtime.mutableStateListOf
import com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation.BottomNavItem
import com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation.ChatNavItem
import com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation.DashboardNavItem
import com.tk.mvi.BaseViewModel

class MainAppViewModel :
    BaseViewModel<MainAppState<AppDestination, BottomNavItem>, MainAppEvent, MainAppIntent>(
        MainAppState(
            tabBackStacks = hashMapOf(
                DashboardNavItem to mutableStateListOf(AppDestination.Dashboard),
                ChatNavItem to mutableStateListOf(AppDestination.Chat)
            ),
            currentTab = DashboardNavItem,
            rootTab = DashboardNavItem,
            combinedBackStack = mutableStateListOf(AppDestination.Dashboard)
        )
    ) {
    override suspend fun handleIntent(intent: MainAppIntent) {
        when (intent) {
            is MainAppIntent.Push -> push(intent.root, intent.destination)
            is MainAppIntent.Pop -> pop()
            is MainAppIntent.SwitchRoot -> switchRoot(intent.root)
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

    private fun push(root: BottomNavItem, destination: AppDestination) {
        state.value.tabBackStacks.getOrPut(root) { mutableStateListOf() }.add(destination)
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