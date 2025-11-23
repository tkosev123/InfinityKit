package com.tk.infinitykit.presentation.navigation.authentication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tk.infinitykit.presentation.features.login.LoginScreenUi
import com.tk.infinitykit.presentation.features.login.LoginViewModel
import com.tk.infinitykit.presentation.features.register.RegisterViewModel
import com.tk.infinitykit.presentation.features.register.RegistrationUi
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
    viewModel: NavAuthenticationViewModel = viewModel()
) {
    val backStack = viewModel.backStack

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AuthenticationScreen.LoginScreen> {
                LoginScreenUi(
                    modifier = modifier,
                    navBackStack = backStack
                )
            }
            entry<AuthenticationScreen.RegisterScreen> {
                val viewModel = RegisterViewModel()
                RegistrationUi(modifier = modifier)
            }
        }
    )
}