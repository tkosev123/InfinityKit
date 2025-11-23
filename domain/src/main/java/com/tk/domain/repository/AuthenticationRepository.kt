package com.tk.domain.repository

import com.tk.domain.models.LoginResult

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): LoginResult
    suspend fun register(email: String, password: String)
    fun logout()
}