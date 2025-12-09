package com.tk.infinitykit.presentation.navigation.auth

import androidx.lifecycle.SavedStateHandle
import com.tk.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authRouteIdentifierMapper: AuthRouteIdentifierMapper
) : BaseViewModel<AuthState, AuthEvent, AuthIntent>(AuthState()) {

    init {
        restoreBackStack()
    }

    override suspend fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Push -> push(intent.key)

            AuthIntent.Pop -> pop()
        }
    }

    private fun push(key: AuthRoute) {
        updateState { push(key) }
        save()
    }

    private fun pop() {
        if (state.value.backStack.isNotEmpty()) {
            updateState { pop() }
            save()
        }
    }

    private fun save() {
        savedStateHandle[KEY_BACK_STACK] = state.value.backStack.map { it.id.toString() }
    }

    private fun restoreBackStack() {
        val savedStack =
            savedStateHandle.get<List<String>>(KEY_BACK_STACK)
                ?.map { authRouteIdentifierMapper.mapToRoute(it) }
                ?.takeIf { it.isNotEmpty() }
                ?: listOf(AuthRoute.LoginScreen)

        updateState { copy(backStack = savedStack) }
    }

    private fun AuthState.push(screen: AuthRoute) =
        copy(backStack = backStack + screen)

    private fun AuthState.pop() =
        copy(backStack = backStack.dropLast(1)
            .ifEmpty { listOf(AuthRoute.LoginScreen) })

    companion object {
        private const val KEY_BACK_STACK = "auth_nav_stack"
    }
}