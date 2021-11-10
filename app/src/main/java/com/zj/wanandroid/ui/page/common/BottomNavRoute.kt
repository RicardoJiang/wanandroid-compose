package com.zj.wanandroid.ui.page.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.zj.wanandroid.R

sealed class BottomNavRoute(
    var routeName: String,
    @StringRes var stringId: Int,
    var icon: ImageVector
) {
    object Home: BottomNavRoute(RouteName.HOME, R.string.home, Icons.Default.Home)
    object Category: BottomNavRoute(RouteName.CATEGORY, R.string.category, Icons.Default.Menu)
    object Collection: BottomNavRoute(RouteName.COLLECTION, R.string.collection, Icons.Default.Favorite)
    object Profile: BottomNavRoute(RouteName.PROFILE, R.string.profile, Icons.Default.Person)
}