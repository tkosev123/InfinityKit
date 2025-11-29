package com.tk.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.tk.domain.models.AuthenticationError
import com.tk.domain.models.LoginResult
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
            return LoginResult.Error(mapFirebaseError(it))
        }
    }

    private fun mapFirebaseError(throwable: Throwable): AuthenticationError {
        return when (throwable) {
            is FirebaseAuthInvalidCredentialsException -> AuthenticationError.Remote.InvalidCredentials
            is FirebaseAuthInvalidUserException -> AuthenticationError.Remote.UserNotFound
            else -> AuthenticationError.Unknown(throwable.message)
        }
    }

    override suspend fun register(email: String, password: String) {
        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .suspendCoroutineResult()
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