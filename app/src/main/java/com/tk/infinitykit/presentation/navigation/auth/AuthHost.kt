package com.tk.infinitykit.presentation.navigation.auth

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tk.infinitykit.presentation.features.login.LoginScreenUi
import com.tk.infinitykit.presentation.features.register.RegisterScreenUi
import com.tk.mvi.MviScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class AuthenticationScreen(val id: AuthenticationNavIdentifiers) : NavKey {
    @Serializable
    data object LoginScreen : AuthenticationScreen(AuthenticationNavIdentifiers.LOGIN)

    @Serializable
    data object RegisterScreen : AuthenticationScreen(AuthenticationNavIdentifiers.REGISTER)

    companion object {
        fun fromId(id: AuthenticationNavIdentifiers): AuthenticationScreen = when (id) {
            AuthenticationNavIdentifiers.LOGIN -> LoginScreen
            AuthenticationNavIdentifiers.REGISTER -> RegisterScreen
        }
    }

    enum class AuthenticationNavIdentifiers {
        LOGIN,
        REGISTER
    }
}

@Composable
fun AuthHost(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    MviScreen(
        stateFlow = viewModel.state,
        eventFlow = viewModel.events,
        onEvent = { },
        content = { state ->
            val backStack = state.backStack
            AuthNavGraph(
                modifier = modifier,
                backStack = backStack,
                viewModel = viewModel
            )
        })
}

@Composable
fun AuthNavGraph(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    backStack: List<AuthenticationScreen>,
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { viewModel.onIntent(AuthIntent.Pop) },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AuthenticationScreen.LoginScreen> {
                LoginScreenUi(
                    goToRegistration = {
                        viewModel.onIntent(AuthIntent.Navigate(AuthenticationScreen.RegisterScreen))
                    }
                )
            }
            entry<AuthenticationScreen.RegisterScreen> {
                RegisterScreenUi()
            }
        },
        transitionSpec = {
            ContentTransform(
                targetContentEnter = slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                ) + fadeIn(),
                initialContentExit = slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { -it / 3 }
                ) + fadeOut()
            )
        },
        popTransitionSpec = {
            ContentTransform(
                targetContentEnter = slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { -it }
                ) + fadeIn(),
                initialContentExit = slideOutHorizontally(
                    animationSpec = tween(300),
                    targetOffsetX = { it / 3 }
                ) + fadeOut()
            )
        }
    )
}