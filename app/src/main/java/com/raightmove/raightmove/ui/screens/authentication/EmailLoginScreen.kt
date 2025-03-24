package com.raightmove.raightmove.ui.screens.authentication

import Destinations.HOME_ROUTE
import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raightmove.raightmove.R
import com.raightmove.raightmove.ui.components.ProgressIndicator
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.ui.themes.DarkBronze
import com.raightmove.raightmove.viewmodels.LoginUiState

@Composable
fun EmailLoginScreen(
    navController: NavController? = null,
    loginUIState: LoginUiState,
    error: String?,
    isLoading: Boolean,
    isSuccessLogin: Boolean,
    onEnter: () -> Unit,
    loginUser: (Context) -> Unit,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEnter()
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.dumbbells),
            contentDescription = "App icon",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            tint = Color.Unspecified
        )
        Text(
            text = "Log into account",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = DarkBronze,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = loginUIState.userName,
                onValueChange = { onUserNameChange(it) },
                label = { Text("Email") },
                textStyle = TextStyle(color = Bronze),
                modifier = Modifier.fillMaxWidth(),
                isError = error != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                )
            )
            OutlinedTextField(
                value = loginUIState.password,
                onValueChange = { onPasswordChange(it) },
                label = { Text("Password (6+ characters)") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Bronze),
                visualTransformation = PasswordVisualTransformation(),
                isError = error != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                )
            )
            if (error != null) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = error.toString(),
                    color = Color.Red
                )
            }
            if (isLoading) {
                ProgressIndicator()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    loginUser(context)
                }, modifier = Modifier.fillMaxWidth(), colors = ButtonColors(
                    contentColor = Cream,
                    containerColor = Bronze,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text("Continue")
            }
            if (isSuccessLogin) {
                navController?.navigate(HOME_ROUTE)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmailLoginScreen() {
    EmailLoginScreen(
        loginUIState = LoginUiState(userName = "", password = ""),
        error = null,
        isLoading = false,
        isSuccessLogin = false,
        onEnter = {},
        loginUser = {},
        onUserNameChange = {},
        onPasswordChange = {}
    )
}
