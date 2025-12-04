package com.tk.domain.repository

interface UserRepository {
    suspend fun createUserInDatabase(
        uid: String,
        email: String,
        firstName: String,
        lastName: String
    )
}