@file:OptIn(ExperimentalMaterial3Api::class)

package com.tk.infinitykit.presentation.navigation.inapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tk.infinitykit.presentation.features.chat.ChatScreenUi
import com.tk.infinitykit.presentation.features.dashboard.DashboardScreen
import com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation.BottomNavigationView
import com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation.ChatNavItem
import com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation.DashboardNavItem
import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination : NavKey {
    @Serializable
    data object Dashboard : AppDestination()

    @Serializable
    data object Chat : AppDestination()

    @Serializable
    data object Screen1 : AppDestination()
}

@Composable
fun MainAppNavHost(viewModel: MainAppViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val selectedItem = state.currentTab
    val backStack = state.combinedBackStack
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(bottomBar = {
            BottomNavigationView(
                modifier = Modifier,
                selectedItem = selectedItem,
                onItemClick = { item ->
                    viewModel.onIntent(MainAppIntent.SwitchRoot(item))
                })
        }) { innerPadding ->
            NavDisplay(
                backStack = backStack.toList(),
                onBack = { viewModel.onIntent(MainAppIntent.Pop) },
                entryProvider = entryProvider {
                    entry<AppDestination.Dashboard> {
                        DashboardScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            goToNextScreen = {
                                viewModel.onIntent(
                                    MainAppIntent.Push(
                                        DashboardNavItem,
                                        AppDestination.Screen1
                                    )
                                )
                            }
                        )
                    }

                    entry<AppDestination.Chat> {
                        ChatScreenUi(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            goToNextScreen = {
                                viewModel.onIntent(
                                    MainAppIntent.Push(
                                        ChatNavItem,
                                        AppDestination.Screen1
                                    )
                                )
                            }
                        )
                    }

                    entry<AppDestination.Screen1> {
                        Screen1(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            goToNextScreen = {}
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun Screen1(
    modifier: Modifier = Modifier,
    goToNextScreen: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
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

@Preview(showBackground = true)
@Composable
fun Preview() {
   // MainAppNavHost(
   //
   // )
}