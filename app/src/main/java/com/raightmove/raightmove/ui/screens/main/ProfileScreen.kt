package com.raightmove.raightmove.ui.screens.main

import Destinations.AUTHENTICATION_ROUTE
import Destinations.PROFILE_ROUTE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raightmove.raightmove.models.User
import com.raightmove.raightmove.ui.components.BottomMainNavBar
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream

@Composable
fun ProfileScreen(
    navController: NavController? = null, userInfo: User?, logOut: () -> Unit
) {
    Scaffold(bottomBar = { BottomMainNavBar(PROFILE_ROUTE, navController) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp)
                    .background(
                        color = Color.White
                    )
                    .padding(6.dp),
            ) {
                Text(
                    text = "Profile",
                    modifier = Modifier.padding(24.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
            userInfo?.let {
                Text(it.nick, modifier = Modifier.padding(10.dp))
                Text(it.gender)
                Text(it.age.toString())
                Text(it.height.toString())
            }
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), onClick = {
                    logOut()
                    navController!!.navigate(AUTHENTICATION_ROUTE)
                }, colors = ButtonColors(
                    contentColor = Cream,
                    containerColor = Bronze,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(text = "Sign out")
            }
        }
    }
}
