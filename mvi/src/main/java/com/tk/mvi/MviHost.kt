package com.tk.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <STATE, EVENT> MviHost(
    stateFlow: StateFlow<STATE>,
    eventFlow: SharedFlow<EVENT>,
    onEvent: (EVENT) -> Unit,
    content: @Composable (STATE) -> Unit
) {
    val state by stateFlow.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            eventFlow.collect { onEvent(it) }
        }
    }

    content(state)
}
