package com.tk.infinitykit.presentation.features.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


sealed interface CanvasIntent {
    object OnClear : CanvasIntent
    data class OnColorChanged(val color: Color) : CanvasIntent
    object OnNewPathStart : CanvasIntent
    object OnPathEnd : CanvasIntent
    data class OnDraw(val offset: Offset) : CanvasIntent
}

sealed interface CanvasEvent

data class CanvasState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList()
)

data class PathData(
    val id: String,
    val color: Color,
    val path: List<Offset>
)