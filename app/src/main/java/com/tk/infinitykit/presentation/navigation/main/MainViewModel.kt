package com.tk.infinitykit.presentation.navigation.main

import androidx.compose.runtime.mutableStateListOf
import com.tk.domain.usecase.LogoutUseCase
import com.tk.infinitykit.presentation.components.BottomNavItem
import com.tk.infinitykit.presentation.components.ChatNavItem
import com.tk.infinitykit.presentation.components.DashboardNavItem
import com.tk.infinitykit.presentation.components.FabMenuItem
import com.tk.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase) :
    MviViewModel<MainState<AppRoute, BottomNavItem>, MainEvent, MainIntent>(
        initialState = MainState(
            tabBackStacks = hashMapOf(
                DashboardNavItem to mutableStateListOf(AppRoute.Dashboard),
                ChatNavItem to mutableStateListOf(AppRoute.ConversationListPreview)
            ),
            currentTab = DashboardNavItem,
            rootTab = DashboardNavItem,
            combinedBackStack = mutableStateListOf(AppRoute.Dashboard),
        )
    ) {

    override suspend fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.Push -> push(intent.root, intent.route)
            is MainIntent.Pop -> pop()
            is MainIntent.SwitchRoot -> switchRoot(intent.root)
            is MainIntent.ToggleFab -> updateState { copy(isFabMenuExpanded = !isFabMenuExpanded) }
            is MainIntent.FabMenuItemClick -> handleFabClick(intent.type)
        }
    }

    private fun handleFabClick(type: FabMenuItem.FabType) {
        when (type) {
            FabMenuItem.FabType.CANVAS -> {
                updateState { copy(isFabMenuExpanded = false, isBottomNavVisible = false) }
                push(DashboardNavItem, AppRoute.Canvas)
            }

            FabMenuItem.FabType.LOGOUT -> logoutUseCase.invoke()
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
            val currentStack = newStack[currentTab] ?: return@updateState (this)

            if (currentStack.size > 1) {
                currentStack.removeLastOrNull()
                newStack[currentTab] = currentStack
                return@updateState (
                        copy(
                            tabBackStacks = newStack,
                            isBottomNavVisible = currentStack.size == 1,
                            isFabMenuExpanded = false
                        )
                )
            }

            if (currentTab != rootTab) {
                return@updateState (copy(currentTab = rootTab, isFabMenuExpanded = false))
            }
            this.copy(isFabMenuExpanded = false)
        }
        updateCombinedBackStack()
    }

    private fun push(root: BottomNavItem, route: AppRoute) {
        state.value.tabBackStacks.getOrPut(root) { mutableStateListOf() }.add(route)
        updateCombinedBackStack()
    }

    private fun updateCombinedBackStack() {
        val currentStack = state.value.tabBackStacks[state.value.currentTab] ?: return
        if (state.value.currentTab == state.value.rootTab) {
            state.value.combinedBackStack.clear()
            state.value.combinedBackStack.addAll(currentStack)
        } else {
            val firstBackStack = state.value.tabBackStacks[state.value.rootTab] ?: return
            state.value.combinedBackStack.clear()
            state.value.combinedBackStack.addAll(firstBackStack + currentStack)
        }
    }
}