package com.tk.infinitykit.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tk.infinitykit.presentation.theme.TextSizes
import com.tk.infinitykit.presentation.theme.spacing

@Composable
fun IKDialog(
    modifier: Modifier,
    title: String,
    message: String,
    buttonText: String,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest) {
        Box(
            modifier
                .clip(RoundedCornerShape(16))
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title,
                    fontSize = TextSizes.M,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.4.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium,
                        top = MaterialTheme.spacing.medium
                    )
                )

                Text(
                    message,
                    fontSize = TextSizes.XS,
                    color = Color.Black,
                    letterSpacing = 0.08.sp,
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium,
                        top = MaterialTheme.spacing.extraSmall
                    )
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.medium)
                )

                OutlinedButton(
                    { onDismissRequest() },
                    modifier = Modifier.fillMaxWidth(),
                    border = null,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        buttonText, color = Color.Blue, fontSize = TextSizes.M,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IKDialogPreview() {
    IKDialog(
        modifier = Modifier.padding(MaterialTheme.spacing.medium),
        title = "Delete Item",
        message = "Are you sure you want to delete this item?",
        buttonText = "OK",
        onDismissRequest = {}
    )
}