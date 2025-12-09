package com.tk.infinitykit.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.navigation3.runtime.NavKey
import com.tk.infinitykit.R
import com.tk.infinitykit.presentation.theme.spacing
import kotlinx.serialization.Serializable

@Composable
fun IKBottomNavigationView(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavItem,
    onItemClick: (BottomNavItem) -> Unit
) {
    val navItems = listOf(DashboardNavItem, ChatNavItem)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding()
            .clip(RoundedCornerShape(MaterialTheme.spacing.large)),
        contentAlignment = Alignment.BottomStart

    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.inverseSurface
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
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.primaryFixedDim
                            )
                        )
                    },
                    label = {
                        Text(stringResource(item.title))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.primaryFixedDim
                    )
                )
            }
        }
    }
}

interface BottomNavItem {
    val icon: Int
    val title: Int
}

@Serializable
data object DashboardNavItem : NavKey, BottomNavItem {
    @DrawableRes
    override val icon: Int = R.drawable.ic_bottom_navigation_home

    @StringRes
    override val title: Int = R.string.tab_home
}

@Serializable
data object ChatNavItem : NavKey, BottomNavItem {
    @DrawableRes
    override val icon: Int = R.drawable.ic_bottom_navigation_chat

    @StringRes
    override val title: Int = R.string.tab_chat
}

@Preview(showBackground = true)
@Composable
fun IKBottomNavigationViewPreview() {
    IKBottomNavigationView(
        modifier = Modifier,
        selectedItem = DashboardNavItem,
        onItemClick = {}
    )
}