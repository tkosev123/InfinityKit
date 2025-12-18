package com.tk.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class MviViewModel<S, E, I>(initialState: S) : ViewModel() {
    private val stateMutex = Mutex()

    private  val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected suspend fun updateState(reducer: S.() -> S) {
        stateMutex.withLock {
            _state.update { state -> state.reducer() }
        }
    }

    private val _events = MutableSharedFlow<E>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    private val intentScope = viewModelScope + Dispatchers.Default.limitedParallelism(1)

    fun onIntent(intent: I) {
        intentScope.launch {
            handleIntent(intent)
        }
    }

    protected abstract suspend fun handleIntent(intent: I)
}