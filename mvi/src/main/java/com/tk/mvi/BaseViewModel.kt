package com.tk.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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
    //  UI EVENTS (Hot SharedFlow OR Channel — no replay)
    // -------------------------------------------------------
    private val _events = Channel<E>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _events.send(event)
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