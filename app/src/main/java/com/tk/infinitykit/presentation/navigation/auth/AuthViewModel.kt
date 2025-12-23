package com.tk.infinitykit.presentation.navigation.auth

import androidx.lifecycle.SavedStateHandle
import com.tk.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authRouteIdentifierMapper: AuthRouteIdentifierMapper
) : MviViewModel<AuthState, AuthEvent, AuthIntent>(
    AuthState(
        backStack = restoreBackStack(
            savedStateHandle,
            authRouteIdentifierMapper
        )
    )
) {
    override suspend fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Push -> push(intent.key)

            AuthIntent.Pop -> pop()
        }
    }

    private suspend fun push(key: AuthRoute) {
        updateState { push(key) }
        save()
    }

    private suspend fun pop() {
        if (state.value.backStack.isNotEmpty()) {
            updateState { pop() }
            save()
        }
    }

    private fun save() {
        savedStateHandle[KEY_BACK_STACK] = state.value.backStack.map { it.id.toString() }
    }

    private fun AuthState.push(screen: AuthRoute) =
        copy(backStack = backStack + screen)

    private fun AuthState.pop() =
        copy(
            backStack = backStack.dropLast(1)
                .ifEmpty { listOf(AuthRoute.LoginScreen) })

    companion object {
        private const val KEY_BACK_STACK = "auth_nav_stack"

        fun restoreBackStack(
            savedStateHandle: SavedStateHandle,
            mapper: AuthRouteIdentifierMapper
        ): List<AuthRoute> {
            return savedStateHandle
                .get<List<String>>(KEY_BACK_STACK)
                ?.map(mapper::mapToRoute)
                ?.takeIf(List<AuthRoute>::isNotEmpty)
                ?: listOf(AuthRoute.LoginScreen)
        }
    }
}
