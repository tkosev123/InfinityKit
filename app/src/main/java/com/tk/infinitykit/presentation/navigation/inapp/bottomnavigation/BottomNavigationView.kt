package com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BottomNavigationView(
    modifier: Modifier,
    selectedItem: BottomNavItem,
    onItemClick: (BottomNavItem) -> Unit
) {
    val navItems = listOf(DashboardNavItem, ChatNavItem)

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary
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
                        colorFilter = if (isSelected) ColorFilter.tint(MaterialTheme.colorScheme.primary) else ColorFilter.tint(
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedTextColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                ),
                label = {
                    Text(
                        stringResource(item.title),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}