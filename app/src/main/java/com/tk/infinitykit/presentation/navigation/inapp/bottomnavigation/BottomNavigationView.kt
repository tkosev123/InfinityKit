package com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tk.infinitykit.presentation.theme.spacing

@Composable
fun BottomNavigationView(
    modifier: Modifier,
    selectedItem: BottomNavItem,
    onItemClick: (BottomNavItem) -> Unit
) {
    val navItems = listOf(DashboardNavItem, ChatNavItem)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding()
            .clip(RoundedCornerShape(MaterialTheme.spacing.large))
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.BottomStart

    ) {
        NavigationBar(
            containerColor = Color.Transparent,
        ) {
            navItems.forEach { item ->
                val isSelected = item == selectedItem
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = stringResource(item.title),
                            colorFilter = ColorFilter.tint(
                                if (isSelected) MaterialTheme.colorScheme.secondary
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    },
                    label = {
                        Text(stringResource(item.title))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BottomNavigationView(
        modifier = Modifier,
        selectedItem = DashboardNavItem,
        onItemClick = {}
    )
}