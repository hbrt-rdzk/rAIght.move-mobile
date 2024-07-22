package com.raightmove.raightmove.ui.screens.authentication

import Destinations.HOME_ROUTE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel
import com.raightmove.raightmove.viewmodels.UserInfoViewModel


@Composable
fun UserInfoScreen(
    navController: NavController? = null,
    userInfoViewModel: UserInfoViewModel,
    authenticationViewModel: AuthenticationViewModel
) {
    val sexOptions = listOf("Male", "Female")

    val userInfoUiState = userInfoViewModel.userInfoUiState
    val isError = userInfoUiState.error != null
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = userInfoUiState.nick.orEmpty(),
            onValueChange = { userInfoViewModel.onNickChange(it) },
            label = { Text("User name") },
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
            value = userInfoUiState.age.orEmpty(),
            onValueChange = {
                userInfoViewModel.onAgeChange(it)
            },
            label = { Text("Age") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError
        )
        OutlinedTextField(
            value = userInfoUiState.height.orEmpty(),
            onValueChange = { userInfoViewModel.onHeightChange(it) },
            label = { Text("Height") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError
        )
        Column {
            OutlinedTextField(
                value = userInfoUiState.sex.orEmpty(),
                readOnly = true,
                onValueChange = { },
                label = { Text("Gender") },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        expanded = it.isFocused
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                ),
                isError = isError
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            sexOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        userInfoViewModel.onSexChange(option)
                        focusManager.clearFocus()
                    })
            }
        }

        if (isError) {
            Text(
                text = userInfoUiState.error.toString(),
                color = Color.Red
            )
        }
        if (userInfoUiState.isLoading) {
            CircularProgressIndicator()
        }
        Button(
            onClick = {
                val userId = authenticationViewModel.userId
                userInfoViewModel.addUser(context, userId)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp)
        ) {
            Text("Continue")
        }
        if (userInfoUiState.isSuccessfullyAdded) {
            navController?.navigate(HOME_ROUTE)
        }
    }
}
