package com.tk.infinitykit.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.theme.spacing

@Composable
fun IKSearchView(
    modifier: Modifier = Modifier,
    searchText: String,
    onTextChanged: (String) -> Unit = {}
) {
    TextField(
        searchText,
        modifier = modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(size = MaterialTheme.spacing.large)
            ),
        singleLine = true,
        leadingIcon =  {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        onValueChange = { onTextChanged(it) },
        colors = TextFieldDefaults.colors().copy(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background
        ),
        placeholder = {
            Text("Search")
        })
}

@Preview(showBackground = true)
@Composable
fun IKSearchViewPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {

        IKSearchView(
            modifier = Modifier,
            searchText = ""
        )
    }
}
