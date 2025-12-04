package com.tk.infinitykit.presentation.root

import com.tk.domain.usecase.ObserveAuthStateUseCase
import com.tk.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppRootViewModel @Inject constructor(
    private val observeAuthStateUseCase: ObserveAuthStateUseCase
) : BaseViewModel<AppRootState, AppRootEvent, AppRootIntent>(initialState = AppRootState()) {

    override suspend fun handleIntent(intent: AppRootIntent) =
        when (intent) {
            AppRootIntent.ObserveAuthState -> observeAuthState()
        }

    private suspend fun observeAuthState() {
        observeAuthStateUseCase()
            .collect { loggedIn ->
                updateState { copy(isLoggedIn = loggedIn, isLoading = false) }
            }
    }
}