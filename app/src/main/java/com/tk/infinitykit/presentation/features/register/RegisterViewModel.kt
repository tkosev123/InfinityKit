package com.tk.infinitykit.presentation.features.register

import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
    init {
        println("Initialized RegisterViewModel")
    }

    override fun onCleared() {
        super.onCleared()
        println("Cleared RegisterViewModel")
    }
}