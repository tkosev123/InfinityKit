package com.tk.infinitykit.presentation.features.login

import com.tk.domain.models.CredentialsValidationError
import com.tk.domain.models.LoginError
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
            is LoginIntent.EmailChanged ->
                updateState {
                    copy(
                        email = intent.email,
                        isEmailError = false,
                        isPasswordError = false,
                        emailErrorText = ""
                    )
                }

            is LoginIntent.PasswordChanged ->
                updateState {
                    copy(
                        password = intent.password,
                        isEmailError = false,
                        isPasswordError = false,
                        passwordErrorText = ""
                    )
                }

            LoginIntent.TogglePasswordVisibility ->
                updateState { copy(isPasswordVisible = !isPasswordVisible) }
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
                handleError(result.error, message)
            }
        }
    }

    private fun handleError(error: LoginError, message: String) {
        when (error) {

            /**
             * NEW: Validation now wraps CredentialsValidationError
             */
            is LoginError.Validation -> handleValidationError(error.error, message)

            LoginError.Remote.InvalidCredentials -> {
                updateState {
                    copy(
                        isEmailError = true,
                        isPasswordError = true,
                        emailErrorText = uiMapper.getIncorrectFieldMessage(),
                        passwordErrorText = uiMapper.getIncorrectFieldMessage()
                    )
                }
            }

            LoginError.Remote.UserNotFound -> {
                updateState {
                    copy(
                        isPasswordError = true,
                        passwordErrorText = message
                    )
                }
            }

            is LoginError.Unknown -> {
                updateState {
                    copy(
                        isPasswordError = true,
                        passwordErrorText = message
                    )
                }
            }
        }
    }

    private fun handleValidationError(
        validationError: CredentialsValidationError,
        message: String
    ) {
        when (validationError) {
            CredentialsValidationError.EmptyEmail ->
                updateState {
                    copy(
                        isEmailError = true,
                        emailErrorText = message
                    )
                }

            CredentialsValidationError.InvalidEmail ->
                updateState {
                    copy(
                        isEmailError = true,
                        emailErrorText = message
                    )
                }

            CredentialsValidationError.EmptyPassword,
            CredentialsValidationError.ShortPassword ->
                updateState {
                    copy(
                        isPasswordError = true,
                        passwordErrorText = message
                    )
                }

            CredentialsValidationError.EmptyEmailPassword ->
                updateState {
                    copy(
                        isEmailError = true,
                        isPasswordError = true,
                        emailErrorText = uiMapper.getEmailEmptyFieldMessage(),
                        passwordErrorText = uiMapper.getPasswordEmptyFieldMessage()
                    )
                }
        }
    }
}