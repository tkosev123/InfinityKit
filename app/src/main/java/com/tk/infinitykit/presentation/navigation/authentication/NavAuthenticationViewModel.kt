package com.tk.infinitykit.presentation.navigation.authentication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavAuthenticationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _backStack = mutableStateListOf<NavKey>().apply {
        val saved = savedStateHandle.get<List<String>>("nav_stack") ?: listOf("login")
        addAll(saved.map(::deserializeKey))
    }

    val backStack: SnapshotStateList<NavKey> = _backStack

    fun navigate(key: NavKey) {
        _backStack += key
        save()
    }

    fun pop() {
        if (_backStack.isNotEmpty()) {
            _backStack.removeLast()
            save()
        }
    }

    private fun save() {
        savedStateHandle["nav_stack"] = _backStack.map(::serializeKey)
    }

    private fun serializeKey(key: NavKey): String = when (key) {
        is AuthenticationScreen.LoginScreen -> "login"
        is AuthenticationScreen.RegisterScreen -> "register${key}"
        else -> ""
    }

    private fun deserializeKey(data: String): NavKey = when {
        data == "login" -> AuthenticationScreen.LoginScreen
        data == "register" -> AuthenticationScreen.RegisterScreen

        else -> AuthenticationScreen.LoginScreen
    }
}