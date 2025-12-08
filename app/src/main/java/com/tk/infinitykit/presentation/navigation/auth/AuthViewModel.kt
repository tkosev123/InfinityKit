package com.tk.infinitykit.presentation.navigation.auth

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import com.tk.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<AuthState, AuthEvent, AuthIntent>(
    AuthState(backStack = mutableStateListOf(AuthenticationScreen.LoginScreen))
) {

    init {
        restoreBackStack()
    }

    override suspend fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Navigate -> navigate(intent.key)

            AuthIntent.Pop -> pop()
        }
    }

    private fun navigate(key: AuthenticationScreen) {
        state.value.backStack.add(key)
        save()
    }

    private fun pop() {
        if (state.value.backStack.isNotEmpty()) {
            state.value.backStack.removeLastOrNull()
            save()
        }
    }

    private fun save() {
        savedStateHandle[KEY] = state.value.backStack.map { it.id.toString() }
    }

    private fun restoreBackStack() {
        val saved = savedStateHandle.get<List<String>>(KEY) ?: emptyList()
        val restored = saved.map {
            AuthenticationScreen.fromId(
                AuthenticationScreen.AuthenticationNavIdentifiers.valueOf(it)
            )
        }
        state.value.backStack.apply {
            clear()
            addAll(restored.ifEmpty { listOf(AuthenticationScreen.LoginScreen) })
        }
    }

    companion object {
        private const val KEY = "nav_stack"
    }
}