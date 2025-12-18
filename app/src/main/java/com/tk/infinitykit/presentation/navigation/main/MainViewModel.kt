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
                tabBackStacks = mapOf(
                    DashboardNavItem to listOf(AppRoute.Dashboard),
                    ChatNavItem to listOf(AppRoute.ConversationListPreview)
                ),
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
        updateState {
            val config = backStackConfig
            val tabStack = config.tabBackStacks[root] ?: listOf(AppRoute.Dashboard)

            val combined = buildCombinedBackStack(
                rootTab = config.rootTab,
                currentTab = root,
                tabBackStacks = config.tabBackStacks + (root to tabStack)
            )

            copy(
                backStackConfig = config.copy(
                    currentTab = root,
                    combinedBackStack = combined,
                    tabBackStacks = config.tabBackStacks + (root to tabStack)
                ),
                isBottomNavVisible = true,
                isFabMenuExpanded = false
            )
        }
    }

    private suspend fun pop() {
        updateState {
            val config = backStackConfig
            val currentTab = config.currentTab
            val currentStack = config.tabBackStacks[currentTab] ?: return@updateState this

            if (currentStack.size > 1) {
                val updatedStack = currentStack.dropLast(1)
                val updatedStacks =
                    config.tabBackStacks + (currentTab to updatedStack)

                val combined = buildCombinedBackStack(
                    rootTab = config.rootTab,
                    currentTab = currentTab,
                    tabBackStacks = updatedStacks
                )

                return@updateState copy(
                    backStackConfig = config.copy(
                        tabBackStacks = updatedStacks,
                        combinedBackStack = combined
                    ),
                    isBottomNavVisible = updatedStack.size == 1,
                    isFabMenuExpanded = false
                )
            }

            if (currentTab != config.rootTab) {
                val combined = buildCombinedBackStack(
                    rootTab = config.rootTab,
                    currentTab = config.rootTab,
                    tabBackStacks = config.tabBackStacks
                )

                return@updateState copy(
                    backStackConfig = config.copy(
                        currentTab = config.rootTab,
                        combinedBackStack = combined
                    ),
                    isFabMenuExpanded = false
                )
            }

            copy(isFabMenuExpanded = false)
        }
    }

    private suspend fun push(root: BottomNavItem, route: AppRoute) {
        updateState {
            val config = backStackConfig
            val currentStack = config.tabBackStacks[root] ?: return@updateState this

            val updatedStacks =
                config.tabBackStacks + (root to (currentStack + route))

            val combined = buildCombinedBackStack(
                rootTab = config.rootTab,
                currentTab = root,
                tabBackStacks = updatedStacks
            )

            copy(
                backStackConfig = config.copy(
                    currentTab = root,
                    tabBackStacks = updatedStacks,
                    combinedBackStack = combined
                )
            )
        }
    }

    private fun buildCombinedBackStack(
        rootTab: BottomNavItem,
        currentTab: BottomNavItem,
        tabBackStacks: Map<BottomNavItem, List<AppRoute>>
    ): List<AppRoute> {
        val rootStack = tabBackStacks[rootTab].orEmpty()
        val currentStack = tabBackStacks[currentTab].orEmpty()

        return if (rootTab == currentTab) {
            rootStack
        } else {
            rootStack + currentStack
        }
    }
}