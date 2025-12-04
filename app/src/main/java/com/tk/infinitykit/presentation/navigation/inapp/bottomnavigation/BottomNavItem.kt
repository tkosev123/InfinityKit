package com.tk.infinitykit.presentation.navigation.inapp.bottomnavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.tk.infinitykit.R
import kotlinx.serialization.Serializable

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