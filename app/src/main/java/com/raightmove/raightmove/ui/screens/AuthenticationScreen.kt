@file:JvmName("StartScreenKt")

package com.raightmove.raightmove.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.raightmove.raightmove.R
import com.raightmove.raightmove.ui.components.GoogleButtonContent
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel

@Composable
fun AuthenticationScreen(
    navController: NavController? = null,
) {
    val loading by remember { mutableStateOf(false) }
    val error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Icon(
            painter = painterResource(id = R.mipmap.ic_launcher_monochrome),
            contentDescription = "App icon",
            modifier = Modifier.size(256.dp),
            tint = Color.Unspecified
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Blue,
                        disabledContainerColor = Color.Gray
                    ),
                    onClick = {
                        navController?.navigate("google_login_screen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 18.dp)
                ) {
                    GoogleButtonContent()
                }
                Button(
                    onClick = {
                        navController?.navigate("email_login_screen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 18.dp),
                    colors = ButtonDefaults.buttonColors(Color.DarkGray)
                ) {
                    Text("Login with email and password")
                }
                Button(
                    onClick = {
                        navController?.navigate("register_screen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 18.dp),
                    colors = ButtonDefaults.buttonColors(Color.DarkGray)
                ) {
                    Text("Register account")
                }
            }
        }
        error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview
@Composable
fun PreviewStartScreen() {
    AuthenticationScreen()
}