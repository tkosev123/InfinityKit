package com.tk.infinitykit.presentation.features.canvas

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.tk.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CanvasViewModel @Inject constructor() : MviViewModel<CanvasState, CanvasEvent, CanvasIntent>(
    initialState = CanvasState()
) {

    init {
        Log.e("CanvasViewModel", "Init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("CanvasViewModel", "onCleared")
    }


    override suspend fun handleIntent(intent: CanvasIntent) {
        when (intent) {
            is CanvasIntent.OnClear -> clearCanvas()
            is CanvasIntent.OnColorChanged -> changeColor(intent.color)
            is CanvasIntent.OnDraw -> onDraw(intent.offset)
            is CanvasIntent.OnPathEnd -> onPathEnd()
            is CanvasIntent.OnNewPathStart -> onNewPathStart()
        }
    }

    private suspend fun clearCanvas() {
        updateState { copy(currentPath = null, paths = emptyList()) }
    }

    private suspend fun changeColor(color: Color) {
        updateState { copy(selectedColor = color) }
    }

    private suspend fun onDraw(offset: Offset) {
        val currentPath = state.value.currentPath ?: return
        updateState {
            copy(
                currentPath = currentPath.copy(path = currentPath.path + offset)
            )
        }
    }

    private suspend fun onPathEnd() {
        val currentPath = state.value.currentPath ?: return
        updateState {
            copy(
                currentPath = null,
                paths = paths + currentPath
            )
        }
    }

    private suspend fun onNewPathStart() {
        updateState {
            copy(
                currentPath = PathData(
                    id = UUID.randomUUID().toString(),
                    color = selectedColor,
                    path = emptyList()
                )
            )
        }
    }
}