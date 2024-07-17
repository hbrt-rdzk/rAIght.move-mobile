package com.raightmove.raightmove.ui.components

import Destinations.CALENDAR_ROUTE
import Destinations.CAMERA_ROUTE
import Destinations.HOME_ROUTE
import Destinations.PROFILE_ROUTE
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class BottomNavigationBar(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomMainNavBar(screenName: String, navController: NavController) {
    val navigationItems = listOf(
        BottomNavigationBar(HOME_ROUTE, Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavigationBar(CAMERA_ROUTE, Icons.Filled.Build, Icons.Outlined.Build),
        BottomNavigationBar(CALENDAR_ROUTE, Icons.Filled.DateRange, Icons.Outlined.DateRange),
        BottomNavigationBar(PROFILE_ROUTE, Icons.Filled.Person, Icons.Outlined.Person),
    )
    return NavigationBar {
        navigationItems.forEach { info ->
            NavigationBarItem(
                selected = info.title == screenName,
                onClick = { navController.navigate(info.title) },
                icon = {
                    Icon(
                        imageVector = if (info.title == screenName) {
                            info.selectedIcon
                        } else info.unselectedIcon,
                        contentDescription = info.title
                    )
                }
            )
        }
    }
}