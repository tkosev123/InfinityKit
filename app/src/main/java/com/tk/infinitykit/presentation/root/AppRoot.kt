package com.tk.infinitykit.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tk.infinitykit.presentation.navigation.auth.AuthNavHost
import com.tk.infinitykit.presentation.navigation.inapp.MainAppNavHost

@Composable
fun AppRoot(
    modifier: Modifier,
    viewModel: AppRootViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(AppRootIntent.ObserveAuthState)
    }

    when {
        state.isLoading -> Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.isLoggedIn -> MainAppNavHost()
        else -> AuthNavHost(modifier)
    }
}