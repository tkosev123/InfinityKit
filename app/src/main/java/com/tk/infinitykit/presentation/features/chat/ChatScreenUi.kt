package com.tk.infinitykit.presentation.features.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.TopSearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.components.IKBackground
import com.tk.infinitykit.presentation.components.IKChatListItem
import com.tk.infinitykit.presentation.components.IKSearchView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenUi(
    modifier: Modifier = Modifier,
    goToNextScreen: () -> Unit
) {
    var search by remember { mutableStateOf("") }

    IKBackground(
        modifier = modifier,
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                IKSearchView(
                    modifier = Modifier.padding(16.dp),
                    searchText = search,
                    onTextChanged = {
                        search = it
                    }
                )
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        30,
                        itemContent = {
                            IKChatListItem(
                                modifier = Modifier,
                                R.drawable.logo,
                                "John",
                                "Hello John",
                                "23:30",
                                1
                            )
                        })
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreenUi(modifier = Modifier, goToNextScreen = {})
}