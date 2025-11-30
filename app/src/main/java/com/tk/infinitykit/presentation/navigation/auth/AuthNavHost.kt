package com.tk.infinitykit.presentation.navigation.auth

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tk.infinitykit.presentation.features.login.LoginScreenUi
import com.tk.infinitykit.presentation.features.register.RegisterScreenUi
import kotlinx.serialization.Serializable

sealed class AuthenticationScreen() : NavKey {
    @Serializable
    data object LoginScreen : AuthenticationScreen()
    @Serializable
    data object RegisterScreen : AuthenticationScreen()
}

@Composable
fun NavigationAuthentication(
    modifier: Modifier = Modifier,
    viewModel: AuthNavViewModel = viewModel()
) {
    val backStack = viewModel.backStack
    NavDisplay(
        backStack = backStack,
        onBack = { viewModel.pop() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AuthenticationScreen.LoginScreen> {
                LoginScreenUi(
                    modifier = modifier,
                    goToHome = {  /* TODO:  Open HOME screen */ },
                    goToRegistration = { viewModel.navigate(AuthenticationScreen.RegisterScreen) }
                )
            }
            entry<AuthenticationScreen.RegisterScreen> {
                RegisterScreenUi(modifier = modifier)
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