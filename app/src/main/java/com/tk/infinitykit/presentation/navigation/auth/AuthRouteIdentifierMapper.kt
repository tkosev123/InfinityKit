package com.tk.infinitykit.presentation.navigation.auth

import com.tk.infinitykit.presentation.navigation.auth.AuthRoute.LoginScreen
import com.tk.infinitykit.presentation.navigation.auth.AuthRoute.RegisterScreen
import javax.inject.Inject

class AuthRouteIdentifierMapper @Inject constructor() {

    fun mapToRoute(id: String): AuthRoute {
        return when (AuthRouteIdentifier.valueOf(id)) {
            AuthRouteIdentifier.LOGIN -> LoginScreen
            AuthRouteIdentifier.REGISTER -> RegisterScreen
        }
    }
}

enum class AuthRouteIdentifier {
    LOGIN,
    REGISTER
}