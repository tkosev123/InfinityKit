package com.tk.infinitykit.presentation.features.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tk.mvi.MviHost
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CanvasScreenUi(
    modifier: Modifier = Modifier,
    viewModel: CanvasViewModel = hiltViewModel(),
) {
    CanvasScreenContent(
        modifier = modifier,
        stateFlow = viewModel.state,
        eventFlow = viewModel.events,
        onDragStart = {
            viewModel.onIntent(CanvasIntent.OnNewPathStart)
        },
        onDragEnd = {
            viewModel.onIntent(CanvasIntent.OnPathEnd)
        },
        onDrag = { change, _ ->
            viewModel.onIntent(CanvasIntent.OnDraw(change.position))
        },
        onDragCancel = {
            viewModel.onIntent(CanvasIntent.OnPathEnd)
        }
    )
}

@Composable
private fun CanvasScreenContent(
    modifier: Modifier = Modifier,
    stateFlow: StateFlow<CanvasState>,
    eventFlow: SharedFlow<CanvasEvent>,
    onDragStart: (Offset) -> Unit ,
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit
) {
    MviHost(
        stateFlow = stateFlow,
        eventFlow = eventFlow,
        onEvent = { /* not used */ },
        content = { state ->
            CanvasScreen(
                modifier = modifier,
                state = state,
                onDragStart = onDragStart,
                onDrag = onDrag,
                onDragEnd = onDragEnd,
                onDragCancel = onDragCancel
            )
        }
    )
}

@Composable
private fun CanvasScreen(
    modifier: Modifier = Modifier,
    state: CanvasState,
    onDragStart: (Offset) -> Unit,
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = onDragStart,
                    onDrag = onDrag,
                    onDragEnd = onDragEnd,
                    onDragCancel = onDragCancel
                )
            }
    ) {
        state.paths.forEach {
            drawPath(
                path = it.toComposePath(),
                color = state.selectedColor,
                style = Stroke(width = 6f)
            )
        }

        state.currentPath?.let {
            drawPath(
                path = it.toComposePath(),
                color = it.color,
                style = Stroke(width = 6f)
            )
        }
    }
}

fun PathData.toComposePath(): Path {
    return Path().apply {
        if (path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)
            path.drop(1).forEach { offset ->
                lineTo(offset.x, offset.y)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasScreenContentPreview() {
    //CanvasScreenContent(
    //    stateFlow = MutableStateFlow(CanvasState()),
    //    eventFlow = MutableSharedFlow()
    //)
}