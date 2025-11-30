package com.tk.repository

import com.google.firebase.auth.FirebaseAuth
import com.tk.domain.models.LoginResult
import com.tk.domain.models.RegisterResult
import com.tk.domain.repository.AuthenticationRepository
import com.tk.mapper.AuthenticationMapper
import com.tk.utils.suspendCoroutineResult
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val mapper: AuthenticationMapper
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String): LoginResult =
        runCatching {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .suspendCoroutineResult()
            return LoginResult.Success
        }.getOrElse {
            return LoginResult.Error(mapper.mapLoginErrors(it))
        }

    override suspend fun register(email: String, password: String): RegisterResult =
        runCatching {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .suspendCoroutineResult()
            return RegisterResult.Success
        }.getOrElse {
            return RegisterResult.Error(mapper.mapRegisterErrors(it))
        }

    override fun logout(): Unit = firebaseAuth.signOut()
}