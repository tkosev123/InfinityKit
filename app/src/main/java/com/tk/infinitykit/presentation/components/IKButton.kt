package com.tk.infinitykit.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tk.infinitykit.presentation.theme.LightColors
import com.tk.infinitykit.presentation.theme.spacing

@Composable
fun GenericButton(
    modifier: Modifier,
    text: String? = null,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.spacing.large))
            .border(
                BorderStroke(MaterialTheme.spacing.tiny, LightColors.primary),
                RoundedCornerShape(MaterialTheme.spacing.medium)
            )
            .height(ButtonDefaults.MinHeight)
            .fillMaxWidth()
            .background(LightColors.primary)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(MaterialTheme.spacing.mediumLarge),
                strokeWidth = MaterialTheme.spacing.extraSmall,
                color = LightColors.background,
                trackColor = LightColors.secondary
            )
        } else {
            Text(
                text.orEmpty(),
                modifier = Modifier,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        GenericButton(
            modifier = Modifier,
            text = "Click me",
            onClick = {})

        GenericButton(
            modifier = Modifier,
            isLoading = true,
            onClick = {})

    }
}