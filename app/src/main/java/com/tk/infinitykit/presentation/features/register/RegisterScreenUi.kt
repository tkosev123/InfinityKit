package com.tk.infinitykit.presentation.features.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.components.GenericButton
import com.tk.infinitykit.presentation.components.IKDialog
import com.tk.infinitykit.presentation.components.IKPasswordInputField
import com.tk.infinitykit.presentation.components.IKTextInputField
import com.tk.infinitykit.presentation.theme.TextSizes
import com.tk.infinitykit.presentation.theme.spacing
import com.tk.mvi.MviScreen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RegisterScreenUi(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    goToLogin: () -> Unit = {}
) {
    RegisterScreenContent(
        modifier = modifier,
        stateFlow = viewModel.state,
        events = viewModel.events,
        goToLogin = goToLogin,
        onEmailChanged = { viewModel.onIntent(RegisterIntent.EmailChanged(it)) },
        onPasswordChanged = { viewModel.onIntent(RegisterIntent.PasswordChanged(it)) },
        onFirstNameChanged = { viewModel.onIntent(RegisterIntent.FirstNameChanged(it)) },
        onLastNameChanged = { viewModel.onIntent(RegisterIntent.LastNameChanged(it)) },
        onRegisterClicked = { email, password, firstName, lastName ->
            viewModel.onIntent(RegisterIntent.Register(email, password, firstName, lastName))
        },
        onPasswordToggle = { viewModel.onIntent(RegisterIntent.TogglePasswordVisibility) }
    )
}

@Composable
private fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    stateFlow: StateFlow<RegisterState>,
    events: SharedFlow<RegisterEvent>,
    goToLogin: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onPasswordToggle: () -> Unit,
    onRegisterClicked: (email: String, password: String, firstName: String, lastName: String) -> Unit
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val state by stateFlow.collectAsState()

    MviScreen(stateFlow = stateFlow, eventFlow = events, onEvent = { event ->
        when (event) {
            is RegisterEvent.ShowError -> errorMessage = event.message
        }
    }, content = {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.register_welcome),
                fontSize = TextSizes.XXL,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.large)
            )

            Image(
                painterResource(
                    R.drawable.ic_infinity_logo
                ),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            IKTextInputField(
                value = state.firstName,
                onValueChange = { onFirstNameChanged(it) },
                label = { Text(stringResource(R.string.register_first_name)) },
                isError = state.isFirstNameError,
                errorText = state.firstNameErrorText,
                modifier = Modifier
                    .fillMaxWidth()
            )

            IKTextInputField(
                value = state.lastName,
                onValueChange = { onLastNameChanged(it) },
                label = { Text(stringResource(R.string.register_last_name)) },
                isError = state.isLastNameError,
                errorText = state.lastNameErrorText,
                modifier = Modifier
                    .fillMaxWidth()
            )

            IKTextInputField(
                value = state.email,
                onValueChange = { onEmailChanged(it) },
                label = { Text(stringResource(R.string.login_email)) },
                isError = state.isEmailError,
                errorText = state.emailErrorText,
                modifier = Modifier
                    .fillMaxWidth()
            )

            IKPasswordInputField(
                value = state.password,
                onValueChange = { onPasswordChanged(it) },
                label = { Text(stringResource(R.string.login_password)) },
                isError = state.isPasswordError,
                errorText = state.passwordErrorText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.mediumLarge),
                isPasswordVisible = state.isPasswordVisible,
                onPasswordToggle = { onPasswordToggle() }
            )

            Spacer(modifier = Modifier.weight(1f))

            GenericButton(
                modifier = Modifier,
                text = stringResource(R.string.register_button_text),
                isLoading = state.isLoading,
                onClick = {
                    onRegisterClicked(
                        state.email,
                        state.password,
                        state.firstName,
                        state.lastName
                    )
                }
            )
        }

        if (errorMessage != null) {
            IKDialog(
                modifier = Modifier,
                title = stringResource(R.string.generic_error_title),
                message = errorMessage ?: "",
                buttonText = stringResource(R.string.generic_action_ok)
            ) {
                errorMessage = null
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenUiPreview(
    @PreviewParameter(RegisterStateProvider::class) state: RegisterState
) {
    RegisterScreenContent(
        stateFlow = MutableStateFlow(state),
        events = MutableSharedFlow(),
        goToLogin = {},
        onEmailChanged = {},
        onPasswordChanged = {},
        onFirstNameChanged = {},
        onLastNameChanged = {},
        onPasswordToggle = {},
        onRegisterClicked = { _, _, _, _ -> }
    )
}

class RegisterStateProvider : PreviewParameterProvider<RegisterState> {
    override val values: Sequence<RegisterState> = sequenceOf(
        RegisterState(),
        RegisterState(
            email = "johndoe@gmail.com",
            password = "123456",
            firstName = "John",
            lastName = "Doe"
        ),
        RegisterState(
            email = "johndoe@gmail.com",
            password = "123456",
            firstName = "John",
            lastName = "Doe",
            isPasswordVisible = true
        ),
        RegisterState(
            email = "johndoe@gmail.com",
            password = "123456",
            firstName = "John",
            lastName = "Doe",
            isLoading = true
        ),
        RegisterState(
            email = "johndoe@gmail.com",
            password = "123456",
            firstName = "John",
            lastName = "Doe",
            isEmailError = true,
            emailErrorText = "Invalid email"
        ),
        RegisterState(
            email = "johndoe@gmail.com",
            password = "123456",
            firstName = "John",
            lastName = "Doe",
            isPasswordError = true,
            passwordErrorText = "Invalid password"
        ),
    )
}