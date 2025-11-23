package com.tk.infinitykit.presentation.features.login

import androidx.lifecycle.SavedStateHandle
import com.tk.domain.models.LoginResult
import com.tk.domain.repository.AuthenticationRepository
import com.tk.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authenticationRepository: AuthenticationRepository
) : BaseViewModel<LoginState, LoginEvent, LoginIntent>(LoginState()) {

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> login(intent.email, intent.password)
            is LoginIntent.EmailChanged -> {
                updateState { copy(email = intent.email)}
            }
            is LoginIntent.PasswordChanged -> {
                updateState { copy(password = intent.password)}
            }
        }
    }

    private suspend fun login(email: String, password: String) {
        updateState { copy(isLoading = true, error = null) }
        when (val result = authenticationRepository.login(email, password)) {
            LoginResult.Success -> {
                updateState { copy(isLoading = false) }
                sendEvent(LoginEvent.NavigateHome)
            }

            is LoginResult.Error -> {
                updateState { copy(isLoading = false) }
                sendEvent(LoginEvent.ShowError(result.error.toString()))
            }
        }
    }
}