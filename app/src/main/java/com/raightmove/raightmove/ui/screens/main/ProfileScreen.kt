package com.raightmove.raightmove.ui.screens.main

import Destinations.PROFILE_ROUTE
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
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel

@Composable
fun ProfileScreen(navController: NavController, authenticationViewModel: AuthenticationViewModel) {
    val user = authenticationViewModel.currentUser


    Scaffold(
    bottomBar = { BottomMainNavBar(PROFILE_ROUTE, navController) }
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