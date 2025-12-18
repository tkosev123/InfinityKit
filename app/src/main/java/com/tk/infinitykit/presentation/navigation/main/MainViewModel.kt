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
    MviViewModel<MainState, MainEvent, MainIntent>(
        initialState = MainState(
            backStackConfig = BackStackConfig(
                tabBackStacks = hashMapOf(
                    DashboardNavItem to mutableStateListOf(AppRoute.Dashboard),
                    ChatNavItem to mutableStateListOf(AppRoute.ConversationListPreview)
                ),
                currentTab = DashboardNavItem,
                rootTab = DashboardNavItem,
                combinedBackStack = mutableStateListOf(AppRoute.Dashboard),
            )
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

    private suspend fun handleFabClick(type: FabMenuItem.FabType) {
        when (type) {
            FabMenuItem.FabType.CANVAS -> {
                updateState { copy(isFabMenuExpanded = false, isBottomNavVisible = false) }
                push(DashboardNavItem, AppRoute.Canvas)
            }

            FabMenuItem.FabType.LOGOUT -> logoutUseCase.invoke()
        }
    }

    private suspend fun switchRoot(root: BottomNavItem) {
        updateState { copy(backStackConfig = backStackConfig.copy(currentTab = root)) }
        updateCombinedBackStack()
    }

    private suspend fun pop() {
        val currentTab = state.value.backStackConfig.currentTab
        updateState {
            val newStack = state.value.backStackConfig.tabBackStacks.toMutableMap()
            val currentStack = newStack[currentTab] ?: return@updateState (this)

            if (currentStack.size > 1) {
                currentStack.removeLastOrNull()
                newStack[currentTab] = currentStack
                return@updateState (
                        copy(
                            backStackConfig = backStackConfig.copy(tabBackStacks = newStack),
                            isBottomNavVisible = currentStack.size == 1,
                            isFabMenuExpanded = false
                        )
                )
            }

            if (currentTab != backStackConfig.rootTab) {
                return@updateState (copy(
                    backStackConfig = backStackConfig.copy(currentTab = backStackConfig.rootTab),
                    isFabMenuExpanded = false
                ))
            }
            this.copy(isFabMenuExpanded = false)
        }
        updateCombinedBackStack()
    }

    private fun push(root: BottomNavItem, route: AppRoute) {
        state.value.backStackConfig.tabBackStacks.getOrPut(root) { mutableStateListOf() }.add(route)
        updateCombinedBackStack()
    }

    private fun updateCombinedBackStack() {
        val currentStack = state.value.backStackConfig.tabBackStacks[state.value.backStackConfig.currentTab] ?: return
        val backStackConfig = state.value.backStackConfig
        if (backStackConfig.currentTab == backStackConfig.rootTab) {
            state.value.apply {
                backStackConfig.combinedBackStack.clear()
                backStackConfig.combinedBackStack.addAll(currentStack)
            }
        } else {
            val firstBackStack = state.value.backStackConfig.tabBackStacks[state.value.backStackConfig.rootTab] ?: return
            state.value.apply {
                backStackConfig.combinedBackStack.clear()
                state.value.backStackConfig.combinedBackStack.addAll(firstBackStack + currentStack)
            }
        }
    }
}