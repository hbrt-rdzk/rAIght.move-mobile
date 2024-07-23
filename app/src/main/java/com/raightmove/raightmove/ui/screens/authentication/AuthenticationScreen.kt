@file:JvmName("StartScreenKt")

package com.raightmove.raightmove.ui.screens.authentication

import Destinations.SIGN_IN_BY_EMAIL_ROUTE
import Destinations.SIGN_IN_BY_GOOGLE_ROUTE
import Destinations.SIGN_UP_ROUTE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raightmove.raightmove.R
import com.raightmove.raightmove.ui.components.GoogleButtonContent
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.ui.themes.DarkBronze

@Composable
fun AuthenticationScreen(
    navController: NavController? = null,
) {
    val loading by remember { mutableStateOf(false) }
    val error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Icon(
            painter = painterResource(id = R.drawable.barbell_icon),
            contentDescription = "App icon",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            tint = Color.Unspecified
        )
        Text(
            text = "rAIght move",
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal,
            color = DarkBronze,
            modifier = Modifier
                .padding(top = 36.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Fix your posture and increase progress with the help of AI.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = DarkBronze,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Cream,
                        contentColor = Color.Blue,
                        disabledContainerColor = Color.Gray
                    ),
                    onClick = {
                        navController?.navigate(SIGN_IN_BY_GOOGLE_ROUTE)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    GoogleButtonContent()
                }
                Button(
                    onClick = {
                        navController?.navigate(SIGN_IN_BY_EMAIL_ROUTE)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = ButtonColors(
                        contentColor = Cream,
                        containerColor = Bronze,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text("Sign in")
                }
                Button(
                    onClick = {
                        navController?.navigate(SIGN_UP_ROUTE)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = ButtonColors(
                        contentColor = Bronze,
                        containerColor = Cream,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text("I'm new, sign me up")
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