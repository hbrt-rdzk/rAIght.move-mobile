package com.raightmove.raightmove.ui.components

import Destinations.CALENDAR_ROUTE
import Destinations.CAMERA_ROUTE
import Destinations.HOME_ROUTE
import Destinations.PROFILE_ROUTE
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raightmove.raightmove.R
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.ui.themes.DarkBronze
import com.raightmove.raightmove.ui.themes.LightBronze

data class BottomNavigationBarItem(
    val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector
)

@Composable
fun BottomMainNavBar(screenName: String, navController: NavController? = null) {
    val navigationItems = listOf(
        BottomNavigationBarItem(
            HOME_ROUTE,
            ImageVector.vectorResource(id = R.drawable.home_icon),
            ImageVector.vectorResource(id = R.drawable.home_icon)
        ),
        BottomNavigationBarItem(
            CAMERA_ROUTE,
            ImageVector.vectorResource(id = R.drawable.analysis_research_icon),
            ImageVector.vectorResource(id = R.drawable.analysis_research_icon)
        ),
        BottomNavigationBarItem(
            CALENDAR_ROUTE,
            ImageVector.vectorResource(id = R.drawable.calendar_icon),
            ImageVector.vectorResource(id = R.drawable.calendar_icon)
        ),
        BottomNavigationBarItem(
            PROFILE_ROUTE,
            ImageVector.vectorResource(id = R.drawable.user_icon),
            ImageVector.vectorResource(id = R.drawable.user_icon),
        ),
    )
    return NavigationBar(
        containerColor = Cream, contentColor = Bronze
    ) {
        navigationItems.forEach { info ->
            NavigationBarItem(colors = NavigationBarItemDefaults.colors(
                selectedIconColor = DarkBronze,
                unselectedIconColor = DarkBronze,
                indicatorColor = LightBronze
            ),

                selected = info.title == screenName,
                onClick = { navController?.navigate(info.title) },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = if (info.title == screenName) {
                            info.selectedIcon
                        } else info.unselectedIcon,
                        contentDescription = info.title
                    )
                })
        }
    }
}

@Preview
@Composable
fun PreviewBottomMainNavBar() {
    BottomMainNavBar("123")
}