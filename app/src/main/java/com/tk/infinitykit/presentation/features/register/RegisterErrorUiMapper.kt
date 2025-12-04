package com.tk.infinitykit.presentation.features.register

import android.content.res.Resources
import com.tk.domain.models.CredentialsValidationError
import com.tk.domain.models.RegisterError
import com.tk.infinitykit.R
import javax.inject.Inject

class RegisterErrorUiMapper @Inject constructor(private val resources: Resources) {
    fun toUiModel(registerError: RegisterError): String =
        when (registerError) {
            RegisterError.Remote.InvalidCredentials -> resources.getString(R.string.login_error_invalid_email_or_password)
            RegisterError.Remote.WeakPassword -> resources.getString(R.string.register_error_weak_password)
            RegisterError.Remote.EmailAlreadyUsed -> resources.getString(R.string.register_error_email_already_used)
            is RegisterError.Validation -> getCredentialsErrorMessage(registerError.error)
            is RegisterError.Remote.Unknown -> resources.getString(R.string.login_error_unexpected)
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

