package com.tk.infinitykit.presentation.features.chatroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@ExperimentalMaterial3Api
@Composable
fun ChatRoomScreenUi(
    modifier: Modifier = Modifier
) {
    val messages = remember { mutableListOf(
        ConversationUiItem.DateDivider("Today"),

        ConversationUiItem.OtherMessage(
            id = "1",
            text = "Hey! How are you?",
            timestamp = System.currentTimeMillis() - 60_000L,
            senderName = "Alice",
            senderAvatarUrl = null
        ),

        ConversationUiItem.MyMessage(
            id = "2",
            text = "I'm good! What about you?",
            timestamp = System.currentTimeMillis() - 30_000L,
            isRead = true
        ),

        ConversationUiItem.OtherMessage(
            id = "3",
            text = "Doing great, just working on the project.",
            timestamp = System.currentTimeMillis() - 20_000L,
            senderName = "Alice",
            senderAvatarUrl = null
        ),

        ConversationUiItem.MyMessage(
            id = "4",
            text = "Nice! Let me know if you need help.",
            timestamp = System.currentTimeMillis() - 10_000L,
            isRead = false
        )
    ) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn {
            items(messages.size) { index ->
                val msg = messages[index]

                when (msg) {
                    is ConversationUiItem.MyMessage -> Text("ME: ${msg.text}")
                    is ConversationUiItem.OtherMessage -> Text("${msg.senderName}: ${msg.text}")
                    is ConversationUiItem.DateDivider -> Text("--- ${msg.date} ---")
                }
            }
        }
    }
}

sealed class ConversationUiItem {

    data class MyMessage(
        val id: String,
        val text: String,
        val timestamp: Long,
        val isRead: Boolean
    ) : ConversationUiItem()

    data class OtherMessage(
        val id: String,
        val text: String,
        val timestamp: Long,
        val senderName: String? = null,
        val senderAvatarUrl: String? = null
    ) : ConversationUiItem()

    data class DateDivider(
        val date: String
    ) : ConversationUiItem()
}
