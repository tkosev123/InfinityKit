package com.tk.infinitykit.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tk.infinitykit.presentation.navigation.auth.AuthHost
import com.tk.infinitykit.presentation.navigation.main.MainHost
import com.tk.mvi.MviScreen
import kotlinx.serialization.Serializable

sealed class Route : NavKey {
    @Serializable
    data object Auth : Route()

    @Serializable
    data object Main : Route()
}

@Composable
fun AppRoot(
    modifier: Modifier,
    viewModel: AppRootViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.onIntent(AppRootIntent.ObserveAuthState)
    }

    MviScreen(
        stateFlow = viewModel.state,
        eventFlow = viewModel.events,
        onEvent = { },
        content = { state ->
            val backStack = state.backStack
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize())
            } else {
                NavDisplay(
                    backStack = backStack,
                    onBack = { viewModel.onIntent(AppRootIntent.Pop) },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entry<Route.Auth> { AuthHost(modifier) }
                        entry<Route.Main> { MainHost() }
                    }
                )
            }
        })
}