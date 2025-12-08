package com.tk.repository

import com.google.firebase.database.FirebaseDatabase
import com.tk.domain.repository.UserRepository
import com.tk.utils.suspendCoroutineResult
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : UserRepository {

    override suspend fun createUserInDatabase(
        uid: String,
        email: String,
        firstName: String,
        lastName: String
    ) {
        val userMap = mapOf(
            EMAIL to email,
            FIRST_NAME to firstName,
            LAST_NAME to lastName
        )

        val databaseReference = firebaseDatabase
            .getReference(USERS_PATH)
            .child(uid)
        databaseReference.setValue(userMap).suspendCoroutineResult()
    }

    companion object {
        private const val USERS_PATH = "users"
        private const val EMAIL = "email"
        private const val FIRST_NAME = "firstName"
        private const val LAST_NAME = "lastName"
    }
}