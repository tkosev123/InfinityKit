package com.tk.infinitykit.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tk.infinitykit.R

@Composable
fun IKFabMenu(
    items: List<FabMenuItem>,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onFabMenuItemClick: (FabMenuItem.FabType) -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (isExpanded) 45f else 0f, label = "fab-rotation")
    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(visible = isExpanded) {
            LazyColumn(Modifier.padding(bottom = 8.dp)) {
                items(items.size) { index ->
                    FabMenuItemRow(
                        fabMenuItem = items[index],
                        onFabMenuItemClick = onFabMenuItemClick
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .rotate(rotation)
                .size(56.dp),
            onClick = { onToggle() },
            containerColor = MaterialTheme.colorScheme.primary,
            content = {
                Image(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        )
    }
}

@Composable
fun FabMenuItemRow(
    fabMenuItem: FabMenuItem,
    onFabMenuItemClick: (FabMenuItem.FabType) -> Unit
) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            Text(text = fabMenuItem.title)
        }
        Spacer(modifier = Modifier.padding(8.dp))

        FloatingActionButton(
            onClick = { onFabMenuItemClick(fabMenuItem.type) },
            modifier = Modifier
                .padding(end = 4.dp)
                .size(48.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(fabMenuItem.icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}

data class FabMenuItem(
    val type: FabType,
    val icon: Int,
    val title: String
) {
    enum class FabType {
        CANVAS,
        LOGOUT
    }
}