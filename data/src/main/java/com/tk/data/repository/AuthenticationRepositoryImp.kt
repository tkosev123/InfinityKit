package com.tk.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.tk.domain.models.LoginError
import com.tk.domain.models.LoginResult
import com.tk.domain.models.RegisterError
import com.tk.domain.models.RegisterResult
import com.tk.domain.repository.AuthenticationRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthenticationRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String) : LoginResult{
        runCatching {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .suspendCoroutineResult()
            return LoginResult.Success
        }.getOrElse {
            return LoginResult.Error(mapLoginErrors(it))
        }
    }

    private fun mapLoginErrors(throwable: Throwable): LoginError {
        return when (throwable) {
            is FirebaseAuthInvalidCredentialsException -> LoginError.Remote.InvalidCredentials
            is FirebaseAuthInvalidUserException -> LoginError.Remote.UserNotFound
            else -> LoginError.Unknown(throwable.message)
        }
    }

    override suspend fun register(email: String, password: String): RegisterResult {
       runCatching {
           firebaseAuth
               .createUserWithEmailAndPassword(email, password)
               .suspendCoroutineResult()
           return RegisterResult.Success
       }.getOrElse {
           return RegisterResult.Error(mapRegisterErrors(it))
       }
    }

    private fun mapRegisterErrors(it: Throwable): RegisterError {
        when (it) {
            is FirebaseAuthUserCollisionException -> return RegisterError.Remote.EmailAlreadyUsed
            is FirebaseAuthWeakPasswordException -> return RegisterError.Remote.WeakPassword
            else -> return RegisterError.Remote.Unknown(it.message)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    suspend fun <T> Task<T>.suspendCoroutineResult(): T =
        suspendCoroutine { continuation ->
            addOnSuccessListener { result -> continuation.resume(result) }
            addOnFailureListener { e -> continuation.resumeWithException(e) }
        }
}