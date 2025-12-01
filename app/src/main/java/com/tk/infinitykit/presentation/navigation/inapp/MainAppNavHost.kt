package com.tk.infinitykit.presentation.navigation.inapp

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainAppNavHost(modifier: Modifier) {

    Scaffold(modifier = modifier) { contentPadding ->
        Text("Hi")
    }
}