package com.tk.infinitykit.presentation.features.register

import com.tk.domain.models.CredentialsValidationError
import com.tk.domain.models.RegisterError
import com.tk.domain.models.RegisterResult
import com.tk.domain.usecase.RegisterUseCase
import com.tk.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val uiMapper: RegisterErrorUiMapper
) : MviViewModel<RegisterState, RegisterEvent, RegisterIntent>(RegisterState()) {

    override suspend fun handleIntent(intent: RegisterIntent) =
        when (intent) {
            is RegisterIntent.Register -> register(
                intent.email,
                intent.password,
                intent.firstName,
                intent.lastName
            )
            is RegisterIntent.EmailChanged ->
                updateState {
                    copy(
                        email = intent.email,
                        isEmailError = false,
                        emailErrorText = ""
                    )
                }

            is RegisterIntent.PasswordChanged ->
                updateState {
                    copy(
                        password = intent.password,
                        isPasswordError = false,
                        passwordErrorText = ""
                    )
                }

            is RegisterIntent.FirstNameChanged ->
                updateState {
                    copy(
                        firstName = intent.firstName,
                        isFirstNameError = false,
                        firstNameErrorText = ""
                    )
                }

            is RegisterIntent.LastNameChanged ->
                updateState {
                    copy(
                        lastName = intent.lastName,
                        isLastNameError = false,
                        lastNameErrorText = ""
                    )
                }

            RegisterIntent.TogglePasswordVisibility ->
                updateState { copy(isPasswordVisible = !isPasswordVisible) }
        }

    private suspend fun register(email: String, password: String, firstName: String, lastName: String) {
        updateState {
            copy(
                isLoading = true,
                isEmailError = false,
                isPasswordError = false,
                isFirstNameError = false,
                isLastNameError = false
            )
        }
        when (val result = registerUseCase(email, password, firstName, lastName)) {
            is RegisterResult.Success -> updateState { copy(isLoading = false) }

            is RegisterResult.Error -> {
                updateState { copy(isLoading = false) }
                val message = uiMapper.toUiModel(result.error)
                handleError(result.error, message)
            }
        }
    }

    private suspend fun handleError(error: RegisterError, message: String) {
        when (error) {
            is RegisterError.Validation -> handleValidationError(error.error, message)

            RegisterError.Remote.InvalidCredentials -> updateState {
                copy(
                    isEmailError = true,
                    isPasswordError = true,
                    emailErrorText = uiMapper.getIncorrectFieldMessage(),
                    passwordErrorText = uiMapper.getIncorrectFieldMessage()
                )
            }

            RegisterError.Remote.WeakPassword -> updateState {
                copy(
                    isPasswordError = true,
                    passwordErrorText = message
                )
            }

            RegisterError.Remote.EmailAlreadyUsed -> updateState {
                copy(
                    isEmailError = true,
                    emailErrorText = message
                )
            }

            is RegisterError.Remote.Unknown -> updateState {
                copy(
                    isPasswordError = true,
                    passwordErrorText = message
                )
            }
        }
    }

    private suspend fun handleValidationError(
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