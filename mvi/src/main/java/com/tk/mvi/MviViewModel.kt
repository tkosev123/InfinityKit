package com.tk.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<S, E, I>(initialState: S) : ViewModel() {
    private  val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun updateState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }

    private val _events = MutableSharedFlow<E>()
    val events = _events.asSharedFlow()

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    fun onIntent(intent: I) {
        viewModelScope.launch {
            handleIntent(intent)
        }
    }

    protected abstract suspend fun handleIntent(intent: I)
}