package com.raightmove.raightmove.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel

@Composable
fun RegisterScreen(
    navController: NavController? = null,
    authenticationViewModel: AuthenticationViewModel
) {
    val loginUiState = authenticationViewModel.loginUIState
    val isError = loginUiState.signUpError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = loginUiState.userNameSignUp,
            onValueChange = { authenticationViewModel.onUserNameSignUp(it) },
            label = { Text("Email") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            isError = isError
        )
        OutlinedTextField(
            value = loginUiState.passwordSignUp,
            onValueChange = { authenticationViewModel.onPasswordSignUp(it) },
            label = { Text("Password (6+ characters)") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            isError = isError
        )
        OutlinedTextField(
            value = loginUiState.confirmPasswordSignUp,
            onValueChange = { authenticationViewModel.onConfirmPasswordSignUp(it) },
            label = { Text("Confirm password") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            isError = isError
        )
        if (isError) {
            Text(
                text = loginUiState.signUpError.toString(),
                color = Color.Red
            )
        }
        if (loginUiState.isLoading) {
            CircularProgressIndicator()
        }
        Button(
            onClick = {
                authenticationViewModel.createUser(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp)
        ) {
            Text("Register user")
        }
        if (loginUiState.isSuccessLogin) {
            navController?.navigate("camera_screen")
        }
    }
}
