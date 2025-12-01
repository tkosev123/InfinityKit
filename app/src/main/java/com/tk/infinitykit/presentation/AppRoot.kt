package com.tk.infinitykit.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    if(state.isLoggedIn) {
        MainAppNavHost(modifier = modifier)
    } else {
        AuthNavHost(modifier = modifier)
    }
}