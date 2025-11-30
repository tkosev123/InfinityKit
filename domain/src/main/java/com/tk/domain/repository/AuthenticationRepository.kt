package com.tk.domain.repository

import com.tk.domain.models.LoginResult
import com.tk.domain.models.RegisterResult

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): LoginResult
    suspend fun register(email: String, password: String): RegisterResult
    fun logout()
}