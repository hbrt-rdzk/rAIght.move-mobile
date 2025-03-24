package com.raightmove.raightmove.ui.screens.authentication

import Destinations.USER_CREATION_ROUTE
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
fun RegisterScreen(
    navController: NavController? = null,
    loginUIState: LoginUiState,
    error: String?,
    isLoading: Boolean,
    isSuccessLogin: Boolean,
    onEnter: () -> Unit,
    createUser: (Context) -> Unit,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
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
            painter = painterResource(id = R.drawable.runners),
            contentDescription = "App icon",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            tint = Color.Unspecified
        )
        Text(
            text = "Create new account",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = DarkBronze,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = loginUIState.userNameSignUp,
                onValueChange = { onUserNameChange(it) },
                label = { Text("Email") },
                textStyle = TextStyle(color = Bronze),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                ),
                isError = error != null
            )
            OutlinedTextField(
                value = loginUIState.passwordSignUp,
                onValueChange = { onPasswordChange(it) },
                label = { Text("Password (6+ characters)") },
                textStyle = TextStyle(color = Bronze),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                ),
                isError = error != null
            )
            OutlinedTextField(
                value = loginUIState.confirmPasswordSignUp,
                onValueChange = { onPasswordConfirmationChange(it) },
                label = { Text("Confirm password") },
                textStyle = TextStyle(color = Bronze),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                ),
                isError = error != null
            )
            if (error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error.toString(), color = Color.Red
                )
            }
            if (isLoading) {
                ProgressIndicator()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    createUser(context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp),
                colors = ButtonColors(
                    contentColor = Cream,
                    containerColor = Bronze,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text("Continue")
            }
            if (isSuccessLogin) {
                navController?.navigate(USER_CREATION_ROUTE)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(
        loginUIState = LoginUiState(
            userNameSignUp = "",
            passwordSignUp = "",
            confirmPasswordSignUp = ""
        ),
        error = null,
        isLoading = false,
        isSuccessLogin = false,
        onEnter = {},
        createUser = {},
        onUserNameChange = {},
        onPasswordChange = {},
        onPasswordConfirmationChange = {}
    )
}

