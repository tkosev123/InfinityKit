package com.tk.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class MviViewModel<S, E, I>(
    initialState: S,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    protected val _events = MutableSharedFlow<E>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val events: SharedFlow<E> = _events.asSharedFlow()

    private val intentScope = viewModelScope + dispatcher

    fun onIntent(intent: I) {
        intentScope.launch {
            handleIntent(intent)
        }
    }

    protected fun updateState(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }

    protected suspend fun sendEvent(event: E) {
        _events.emit(event)
    }

    protected abstract suspend fun handleIntent(intent: I)
}