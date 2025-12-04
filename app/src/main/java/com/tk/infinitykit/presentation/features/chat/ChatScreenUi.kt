package com.tk.infinitykit.presentation.features.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    goToNextScreen: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        TopAppBar(
            title = {
                Text("Chat")
            },
            windowInsets = WindowInsets(0),
        )
        Spacer(Modifier.weight(8f))
        Spacer(Modifier.weight(1f))
        Button(onClick = goToNextScreen) {
            Text("Go to Screen 1")
        }
        Spacer(Modifier.weight(8f))
    }
}