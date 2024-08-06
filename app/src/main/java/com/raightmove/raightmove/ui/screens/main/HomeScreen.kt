package com.raightmove.raightmove.ui.screens.main

import Destinations.HOME_ROUTE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.components.BottomMainNavBar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomMainNavBar(HOME_ROUTE, navController) }
    ) { padding ->
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
                text = "Home",
                modifier = Modifier.padding(24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}