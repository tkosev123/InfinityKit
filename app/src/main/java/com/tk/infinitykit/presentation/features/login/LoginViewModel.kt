package com.tk.infinitykit.presentation.features.login

import com.tk.domain.models.AuthenticationError
import com.tk.domain.models.LoginResult
import com.tk.domain.usecase.LoginUseCase
import com.tk.infinitykit.presentation.features.login.LoginEvent.*
import com.tk.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val uiMapper: LoginErrorUiMapper
) : BaseViewModel<LoginState, LoginEvent, LoginIntent>(LoginState()) {

    override suspend fun handleIntent(intent: LoginIntent) =
        when (intent) {
            is LoginIntent.Login -> login(intent.email, intent.password)
            is LoginIntent.EmailChanged -> updateState {
                copy(
                    email = intent.email,
                    isEmailError = false,
                    isPasswordError = false,
                    emailErrorText = ""
                )
            }

            is LoginIntent.PasswordChanged -> updateState {
                copy(
                    password = intent.password,
                    isEmailError = false,
                    isPasswordError = false,
                    passwordErrorText = ""
                )
            }

            LoginIntent.TogglePasswordVisibility -> {
                updateState { copy(isPasswordVisible = !isPasswordVisible) }
            }
        }

    private suspend fun login(email: String, password: String) {
        updateState { copy(isLoading = true, isEmailError = false, isPasswordError = false) }
        when (val result = loginUseCase(email, password)) {
            LoginResult.Success -> {
                updateState { copy(isLoading = false) }
                sendEvent(NavigateHome)
            }

            is LoginResult.Error -> {
                updateState { copy(isLoading = false) }
                val message = uiMapper.toUiModel(result.error)
                handleError(result, message)
            }
        }
    }

    private fun handleError(result: LoginResult.Error, message: String) =
        when (result.error) {
            AuthenticationError.Validation.EmptyEmail,
            AuthenticationError.Validation.InvalidEmail ->
                updateState {
                    copy(
                        isEmailError = true,
                        emailErrorText = message
                    )
                }

            AuthenticationError.Validation.ShortPassword,
            AuthenticationError.Remote.UserNotFound,
            AuthenticationError.Validation.EmptyPassword ->
                updateState {
                    copy(
                        isPasswordError = true,
                        passwordErrorText = message
                    )
                }

            AuthenticationError.Remote.InvalidCredentials -> {
                updateState {
                    copy(
                        isEmailError = true,
                        isPasswordError = true,
                        emailErrorText = uiMapper.getIncorrectFieldMessage(),
                        passwordErrorText = uiMapper.getIncorrectFieldMessage()
                    )
                }
            }

            AuthenticationError.Validation.EmptyEmailPassword ->
                updateState {
                    copy(
                        isEmailError = true,
                        isPasswordError = true,
                        emailErrorText = uiMapper.getEmailEmptyFieldMessage(),
                        passwordErrorText = uiMapper.getPasswordEmptyFieldMessage()
                    )
                }

            is AuthenticationError.Unknown -> sendEvent(ShowError(message))
        }
}