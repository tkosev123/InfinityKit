package com.tk.infinitykit.presentation.features.login

import android.content.res.Resources
import com.tk.domain.models.AuthenticationError
import com.tk.infinitykit.R
import javax.inject.Inject

class LoginErrorUiMapper @Inject constructor(private val resources: Resources) {
    fun toUiModel(error: AuthenticationError): String {
        return when (error) {
            AuthenticationError.InvalidCredentials ->
                resources.getString(R.string.login_error_invalid_email_or_password)

            AuthenticationError.UserNotFound ->
                resources.getString(R.string.login_error_user_does_not_exist)

            is AuthenticationError.Unknown ->
                error.message ?: resources.getString(R.string.login_error_unexpected)
        }
    }
}