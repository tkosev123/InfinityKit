package com.tk.infinitykit.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.theme.TextSizes
import com.tk.infinitykit.presentation.theme.spacing

@Composable
fun IKChatListItem(
    modifier: Modifier = Modifier,
    avatarRes: Int,
    name: String,
    message: String,
    time: String,
    unreadCount: Int = 0
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    CircleShape
                ),
            painter = painterResource(avatarRes),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.medium)
                .fillMaxHeight()
                .weight(3f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                name,
                fontSize = TextSizes.M,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                message,
                fontSize = TextSizes.S,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = MaterialTheme.spacing.tiny)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.medium)
                .fillMaxHeight()
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                time,
                fontSize = TextSizes.XS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.extraSmall)
                        .clip(CircleShape)
                        .size(22.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        unreadCount.toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = TextSizes.S,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun IKChatListItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {

        IKChatListItem(
            avatarRes = R.drawable.logo,
            name = "Bryan",
            message = "Looks great",
            time = "4:30 PM",
            unreadCount = 2
        )
    }
}
