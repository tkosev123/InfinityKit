package com.tk.infinitykit.presentation.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.components.GenericButton
import com.tk.infinitykit.presentation.components.IKDialog
import com.tk.infinitykit.presentation.navigation.authentication.AuthenticationScreen
import com.tk.infinitykit.presentation.theme.TextSizes
import com.tk.infinitykit.presentation.theme.spacing
import com.tk.mvi.MviScreen

@Composable
fun LoginScreenUi(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navBackStack: SnapshotStateList<NavKey>
) {
    val state by viewModel.state.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    MviScreen(stateFlow = viewModel.state, eventFlow = viewModel.events, onEvent = { event ->
        when (event) {
            is LoginEvent.NavigateHome -> {
                navBackStack.add(AuthenticationScreen.RegisterScreen)
            }

            is LoginEvent.ShowError -> {
                errorMessage = event.message
            }
        }
    }, content = {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_welcome_back),
                fontSize = TextSizes.XXL,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.large)
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onIntent(LoginIntent.EmailChanged(it)) },
                label = { Text(stringResource(R.string.login_email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.medium),
                singleLine = true
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onIntent(LoginIntent.PasswordChanged(it)) },
                label = { Text(stringResource(R.string.login_password)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.mediumLarge),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            GenericButton(
                modifier = Modifier,
                text = stringResource(R.string.login_button),
                isLoading = state.isLoading,
                onClick = {
                    viewModel.onIntent(LoginIntent.Login(state.email, state.password))
                })

            Text(
                text = stringResource(R.string.register_button),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.medium)
                    .clickable {
                        navBackStack.add(AuthenticationScreen.RegisterScreen)
                    })
        }

        if (errorMessage != null) {
            IKDialog(
                modifier = Modifier,
                title = "Title",
                message = errorMessage ?: "",
                buttonText = "OK"
            ) {
                errorMessage = null
            }
        }
    })
}
