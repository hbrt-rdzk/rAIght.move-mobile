package com.raightmove.raightmove.ui.screens.main

import Destinations.HOME_ROUTE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.components.BottomMainNavBar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomMainNavBar(HOME_ROUTE, navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(text = "")
        }
    }
}