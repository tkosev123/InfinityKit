package com.tk.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <STATE, EVENT> MviScreen(
    stateFlow: StateFlow<STATE>,
    eventFlow: SharedFlow<EVENT>,
    onEvent: (EVENT) -> Unit,
    content: @Composable (STATE) -> Unit
) {
    val state by stateFlow.collectAsState()

    LaunchedEffect(eventFlow) {
        eventFlow.collect {
            onEvent(it)
        }
    }

    content(state)
}
