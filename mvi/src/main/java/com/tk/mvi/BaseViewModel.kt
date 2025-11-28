package com.tk.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E, I>(initialState: S) : ViewModel() {
    // -------------------------------------------------------
    //  UI STATE (Cold StateFlow — persists, observable, replay = 1)
    // -------------------------------------------------------
    private  val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected fun updateState(reducer: S.() -> S) {
        _state.value = _state.value.reducer()
    }

    // -------------------------------------------------------
    //  UI EVENTS (Hot SharedFlow — no replay)
    // -------------------------------------------------------
    private val _events = MutableSharedFlow<E>()
    val events = _events.asSharedFlow()

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    // -------------------------------------------------------
    //  INTENTS (User actions handled in one place)
    // -------------------------------------------------------
    fun onIntent(intent: I) {
        viewModelScope.launch {
            handleIntent(intent)
        }
    }

    protected abstract suspend fun handleIntent(intent: I)
}