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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.ui.themes.DarkBronze

data class BottomNavigationBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomMainNavBar(screenName: String, navController: NavController? = null) {
    val navigationItems = listOf(
        BottomNavigationBarItem(HOME_ROUTE, Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavigationBarItem(CAMERA_ROUTE, Icons.Filled.Build, Icons.Outlined.Build),
        BottomNavigationBarItem(CALENDAR_ROUTE, Icons.Filled.DateRange, Icons.Outlined.DateRange),
        BottomNavigationBarItem(PROFILE_ROUTE, Icons.Filled.Person, Icons.Outlined.Person),
    )
    return NavigationBar(
        containerColor = Cream,
        contentColor = Bronze
    ) {
        navigationItems.forEach { info ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = DarkBronze,
                    indicatorColor = Bronze
                ),

                selected = info.title == screenName,
                onClick = { navController?.navigate(info.title) },
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

@Preview
@Composable
fun PreviewBottomMainNavBar() {
    BottomMainNavBar("123")
}