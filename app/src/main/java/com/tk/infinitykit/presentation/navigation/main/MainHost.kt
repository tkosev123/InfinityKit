@file:OptIn(ExperimentalMaterial3Api::class)

package com.tk.infinitykit.presentation.navigation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.components.IKBottomNavigationView
import com.tk.infinitykit.presentation.components.ChatNavItem
import com.tk.infinitykit.presentation.components.DashboardNavItem
import com.tk.infinitykit.presentation.components.FabMenuItem
import com.tk.infinitykit.presentation.components.IKFabMenu
import com.tk.infinitykit.presentation.features.canvas.CanvasScreenUi
import com.tk.infinitykit.presentation.features.chatlist.ConversationListScreenUi
import com.tk.infinitykit.presentation.features.chatroom.ChatRoomScreenUi
import com.tk.infinitykit.presentation.features.dashboard.DashboardScreen
import com.tk.mvi.MviHost
import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoute : NavKey {
    @Serializable
    data object Dashboard : AppRoute()

    @Serializable
    data object ConversationListPreview : AppRoute()

    @Serializable
    data object ChatRoom : AppRoute()

    @Serializable
    data object Screen1 : AppRoute()

    @Serializable
    data object Canvas : AppRoute()
}

@Composable
fun MainHost(
    viewModel: MainViewModel = hiltViewModel()
) {
    MviHost(
        stateFlow = viewModel.state,
        eventFlow = viewModel.events,
        onEvent = { /* not used */ },
        content = { state ->
            val selectedTab = state.currentTab
            val backStack = state.combinedBackStack
            val fabMenuItems = listOf(
                FabMenuItem(type = FabMenuItem.FabType.LOGOUT, R.drawable.ic_logout, "Logout"),
                FabMenuItem(type = FabMenuItem.FabType.CANVAS, R.drawable.ic_canvas, "Canvas"),
            )
            Scaffold(
                bottomBar = {
                    AnimatedVisibility(
                        visible = state.isBottomNavVisible,
                        enter = slideInVertically(
                            initialOffsetY = { fullHeight -> fullHeight }
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { fullHeight -> fullHeight }
                        )) {
                        IKBottomNavigationView(
                            selectedItem = selectedTab,
                            onItemClick = { item ->
                                viewModel.onIntent(MainIntent.SwitchRoot(item))
                            }
                        )
                    }
                },
                floatingActionButton = {
                    IKFabMenu(
                        items = fabMenuItems,
                        isExpanded = state.isFabMenuExpanded,
                        onToggle = { viewModel.onIntent(MainIntent.ToggleFab) },
                        onFabMenuItemClick = { viewModel.onIntent(MainIntent.FabMenuItemClick(it)) }
                    )
                },
                content = { innerPadding ->
                    MainNavGraph(
                        backStack = backStack,
                        innerPadding = innerPadding,
                        viewModel = viewModel
                    )
                }
            )
        }
    )
}

@Composable
fun MainNavGraph(
    backStack: List<NavKey>,
    innerPadding: PaddingValues,
    viewModel: MainViewModel
) {
    NavDisplay(
        backStack = backStack,
        onBack = { viewModel.onIntent(MainIntent.Pop) },
        entryProvider = entryProvider {
            entry<AppRoute.Dashboard> {
                DashboardScreen(
                    modifier = Modifier.padding(innerPadding),
                    goToNextScreen = {
                        viewModel.onIntent(MainIntent.Push(DashboardNavItem, AppRoute.Screen1))
                    }
                )
            }

            entry<AppRoute.ConversationListPreview> {
                ConversationListScreenUi(
                    modifier = Modifier.padding(innerPadding),
                    goToChatRoom = {
                        viewModel.onIntent(
                            MainIntent.Push(
                                ChatNavItem,
                                AppRoute.ChatRoom
                            )
                        )
                    }
                )
            }

            entry<AppRoute.Screen1> { Screen1() }

            entry<AppRoute.ChatRoom> {
                ChatRoomScreenUi(modifier = Modifier.padding(innerPadding))
            }

            entry<AppRoute.Canvas> {
                CanvasScreenUi(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    )
}

@Composable
fun Screen1(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Spacer(Modifier.weight(8f))
        Text("Screen2")
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.weight(8f))
    }
}