package com.tk.infinitykit.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.theme.spacing

@Composable
fun IKTextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String? = null,
) {
    GenericTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        isError = isError,
        errorText = errorText
    )
}

@Composable
fun IKPasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordToggle: () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String? = null
) {
    GenericTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        isError = isError,
        errorText = errorText,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onPasswordToggle) {
                Image(
                    painter = painterResource(
                        if (isPasswordVisible) R.drawable.visibility_off else R.drawable.visibility_on
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    )
}

@Composable
private fun GenericTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            modifier = modifier,
            singleLine = true,
            isError = isError,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorText.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IKInputTextPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        IKTextInputField(
            value = "",
            onValueChange = {},
            label = { Text("Email") },
            isError = false
        )

        IKTextInputField(
            value = "",
            onValueChange = {},
            label = { Text("Email") },
            isError = true
        )

        IKPasswordInputField(
            value = "Test12345",
            onValueChange = {},
            isPasswordVisible = false,
            onPasswordToggle = {},
            label = { Text("Password") },
            isError = false
        )

        IKPasswordInputField(
            value = "Test12345",
            onValueChange = {},
            isPasswordVisible = false,
            onPasswordToggle = {},
            label = { Text("Password") },
            isError = true
        )

        IKPasswordInputField(
            value = "Test12345",
            onValueChange = {},
            isPasswordVisible = true,
            onPasswordToggle = {},
            label = { Text("Password") },
            isError = false
        )

        IKPasswordInputField(
            value = "Test12345",
            onValueChange = {},
            isPasswordVisible = true,
            onPasswordToggle = {},
            label = { Text("Password") },
            isError = true
        )
    }
}