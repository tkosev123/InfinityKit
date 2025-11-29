package com.tk.infinitykit.presentation.features.login

import android.content.res.Resources
import com.tk.domain.models.AuthenticationError
import com.tk.infinitykit.R
import javax.inject.Inject

class LoginErrorUiMapper @Inject constructor(private val resources: Resources) {
    fun toUiModel(error: AuthenticationError): String =
        when (error) {
            AuthenticationError.Remote.InvalidCredentials -> resources.getString(R.string.login_error_invalid_email_or_password)
            AuthenticationError.Remote.UserNotFound -> resources.getString(R.string.login_error_user_does_not_exist)
            AuthenticationError.Validation.EmptyEmail -> resources.getString(R.string.login_error_empty_email)
            AuthenticationError.Validation.EmptyPassword -> resources.getString(R.string.login_error_empty_password)
            AuthenticationError.Validation.InvalidEmail -> resources.getString(R.string.login_error_email_invalid_format)
            AuthenticationError.Validation.ShortPassword -> resources.getString(R.string.login_error_password_below_required_length)
            AuthenticationError.Validation.EmptyEmailPassword -> resources.getString(R.string.login_error_empty_email_password)
            is AuthenticationError.Unknown ->
                error.message ?: resources.getString(R.string.login_error_unexpected)
        }

    fun getIncorrectFieldMessage(): String =
        resources.getString(R.string.login_error_incorrect_field)

    fun getEmailEmptyFieldMessage(): String = resources.getString(R.string.login_error_empty_email)

    fun getPasswordEmptyFieldMessage(): String =
        resources.getString(R.string.login_error_empty_password)
}