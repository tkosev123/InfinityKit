package com.tk.infinitykit.presentation.features.chatlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.tk.infinitykit.presentation.components.IKConversationItem
import com.tk.infinitykit.presentation.components.IKSearchView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreenUi(
    modifier: Modifier = Modifier,
    goToChatRoom: () -> Unit
) {
    var search by remember { mutableStateOf("") }

    IKBackground(
        modifier = modifier,
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
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
                            IKConversationItem(
                                modifier = Modifier,
                                R.drawable.logo,
                                "John",
                                "Hello John",
                                "23:30",
                                1,
                                onClick = { goToChatRoom() }
                            )
                        })
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ConversationListScreenPreview() {
    ConversationListScreenUi(modifier = Modifier, goToChatRoom = {})
}