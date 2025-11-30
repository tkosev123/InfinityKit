package com.tk.infinitykit.presentation.features.login

import android.content.res.Resources
import com.tk.domain.models.CredentialsValidationError
import com.tk.domain.models.LoginError
import com.tk.infinitykit.R
import javax.inject.Inject

class LoginErrorUiMapper @Inject constructor(private val resources: Resources) {
    fun toUiModel(loginError: LoginError): String =
        when (loginError) {
            LoginError.Remote.InvalidCredentials -> resources.getString(R.string.login_error_invalid_email_or_password)
            LoginError.Remote.UserNotFound -> resources.getString(R.string.login_error_user_does_not_exist)
            is LoginError.Validation -> getCredentialsErrorMessage(loginError.error)
            is LoginError.Unknown -> resources.getString(R.string.login_error_unexpected)
        }

    private fun getCredentialsErrorMessage(error: CredentialsValidationError): String =
        when (error) {
            CredentialsValidationError.EmptyEmail -> resources.getString(R.string.login_error_empty_email)
            CredentialsValidationError.EmptyEmailPassword -> resources.getString(R.string.login_error_empty_email_password)
            CredentialsValidationError.EmptyPassword -> resources.getString(R.string.login_error_empty_password)
            CredentialsValidationError.InvalidEmail -> resources.getString(R.string.login_error_email_invalid_format)
            CredentialsValidationError.ShortPassword -> resources.getString(R.string.login_error_password_below_required_length)
        }

    fun getIncorrectFieldMessage(): String =
        resources.getString(R.string.login_error_incorrect_field)

    fun getEmailEmptyFieldMessage(): String = resources.getString(R.string.login_error_empty_email)

    fun getPasswordEmptyFieldMessage(): String =
        resources.getString(R.string.login_error_empty_password)
}