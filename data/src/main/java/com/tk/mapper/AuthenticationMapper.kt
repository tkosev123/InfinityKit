package com.tk.mapper

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.tk.domain.models.LoginError
import com.tk.domain.models.RegisterError
import javax.inject.Inject

class AuthenticationMapper @Inject constructor() {
    fun mapLoginErrors(throwable: Throwable): LoginError =
        when (throwable) {
            is FirebaseAuthInvalidCredentialsException -> LoginError.Remote.InvalidCredentials
            is FirebaseAuthInvalidUserException -> LoginError.Remote.UserNotFound
            else -> LoginError.Unknown(throwable.message)
        }

    fun mapRegisterErrors(it: Throwable): RegisterError =
        when (it) {
            is FirebaseAuthUserCollisionException -> RegisterError.Remote.EmailAlreadyUsed
            is FirebaseAuthWeakPasswordException -> RegisterError.Remote.WeakPassword
            else -> RegisterError.Remote.Unknown(it.message)
        }
}