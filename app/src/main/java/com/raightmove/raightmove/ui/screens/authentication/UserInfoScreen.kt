package com.raightmove.raightmove.ui.screens.authentication

import Destinations.HOME_ROUTE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.raightmove.raightmove.R
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel
import com.raightmove.raightmove.viewmodels.UserInfoViewModel


@Composable
fun UserInfoScreen(
    navController: NavController? = null,
    userInfoViewModel: UserInfoViewModel = viewModel(),
    authenticationViewModel: AuthenticationViewModel = viewModel(),
) {
    val sexOptions = listOf("Male", "Female")

    val userInfoUiState = userInfoViewModel.userInfoUiState
    val isError = userInfoUiState.error != null
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.gym),
            contentDescription = "App icon",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            tint = Color.Unspecified
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = userInfoUiState.nick.orEmpty(),
                onValueChange = { userInfoViewModel.onNickChange(it) },
                label = { Text("User name") },
                textStyle = TextStyle(color = Bronze),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                ),
                isError = isError
            )
            OutlinedTextField(
                value = userInfoUiState.age.orEmpty(),
                onValueChange = {
                    userInfoViewModel.onAgeChange(it)
                },
                label = { Text("Age") },
                textStyle = TextStyle(color = Bronze),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                ),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isError
            )
            OutlinedTextField(
                value = userInfoUiState.height.orEmpty(),
                onValueChange = { userInfoViewModel.onHeightChange(it) },
                label = { Text("Height") },
                textStyle = TextStyle(color = Bronze),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isError
            )
            OutlinedTextField(
                value = userInfoUiState.sex.orEmpty(),
                readOnly = true,
                onValueChange = { },
                label = { Text("Gender") },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        expanded = it.isFocused
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Bronze,
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Bronze
                ),
                isError = isError
            )
            DropdownMenu(
                expanded = expanded, onDismissRequest = {
                    expanded = false
                    focusManager.clearFocus()
                }, modifier = Modifier.fillMaxWidth()
            ) {
                sexOptions.forEach { option ->
                    DropdownMenuItem(text = { Text(text = option) }, onClick = {
                        expanded = false
                        userInfoViewModel.onSexChange(option)
                        focusManager.clearFocus()
                    })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val userId = authenticationViewModel.userId
                    userInfoViewModel.addUser(context, userId)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp), colors = ButtonColors(
                    contentColor = Cream,
                    containerColor = Bronze,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text("Continue")
            }
        }
    }

    if (isError) {
        Text(
            text = userInfoUiState.error.toString(), color = Color.Red
        )
    }
    if (userInfoUiState.isLoading) {
        CircularProgressIndicator()
    }

    if (userInfoUiState.isSuccessfullyAdded) {
        navController?.navigate(HOME_ROUTE)
    }
}


@Preview
@Composable
fun PreviewUserInfoScreen() {
    UserInfoScreen()
}
