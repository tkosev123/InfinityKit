@file:OptIn(ExperimentalMaterial3Api::class)

package com.tk.infinitykit.presentation.navigation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tk.infinitykit.presentation.components.bottomnavigation.BottomNavigationView
import com.tk.infinitykit.presentation.components.bottomnavigation.ChatNavItem
import com.tk.infinitykit.presentation.components.bottomnavigation.DashboardNavItem
import com.tk.infinitykit.presentation.features.chatlist.ConversationListScreenUi
import com.tk.infinitykit.presentation.features.chatroom.ChatRoomScreenUi
import com.tk.infinitykit.presentation.features.dashboard.DashboardScreen
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
}

@Composable
fun MainHost(viewModel: MainViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val selectedItem = state.currentTab
    val backStack = state.combinedBackStack
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                BottomNavigationView(
                    modifier = Modifier,
                    selectedItem = selectedItem,
                    onItemClick = { item ->
                        viewModel.onIntent(MainIntent.SwitchRoot(item))
                    })
            },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("titleaa")
                    },
                    actions = {
                        Text("actions")
                    },
                    colors = TopAppBarDefaults.topAppBarColors().copy(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                )
            })
        { innerPadding ->
            NavDisplay(
                backStack = backStack.toList(),
                onBack = { viewModel.onIntent(MainIntent.Pop) },
                entryProvider = entryProvider {
                    entry<AppRoute.Dashboard> {
                        DashboardScreen(
                            modifier = Modifier
                                .padding(innerPadding),
                            goToNextScreen = {
                                viewModel.onIntent(
                                    MainIntent.Push(
                                        DashboardNavItem,
                                        AppRoute.Screen1
                                    )
                                )
                            }
                        )
                    }

                    entry<AppRoute.ConversationListPreview> {
                        ConversationListScreenUi(
                            modifier = Modifier
                                .padding(innerPadding),
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

                    entry<AppRoute.Screen1> {
                        Screen1()
                    }

                    entry<AppRoute.ChatRoom> {
                        ChatRoomScreenUi(modifier = Modifier.padding(innerPadding))
                    }
                }
            )
        }
    }
}

@Composable
fun Screen1(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text("Screen2")
            },
            windowInsets = WindowInsets(0),
        )
        Spacer(Modifier.weight(8f))
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.weight(8f))
    }
}