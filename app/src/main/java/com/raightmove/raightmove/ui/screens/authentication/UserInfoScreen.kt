package com.raightmove.raightmove.ui.screens.authentication

import Destinations.HOME_ROUTE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raightmove.raightmove.R
import com.raightmove.raightmove.models.User
import com.raightmove.raightmove.ui.components.ProgressIndicator
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.ui.themes.DarkBronze
import com.raightmove.raightmove.viewmodels.UserInfoUiState


@Composable
fun UserInfoScreen(
    navController: NavController? = null,
    userInfoUIState: UserInfoUiState,
    error: String?,
    isLoading: Boolean,
    userInfo: User?,
    getUserID: () -> String,
    createUserInfo: (String) -> Unit,
    onNickChange: (String) -> Unit,
    onSexChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onAgeChange: (String) -> Unit
) {
    val sexOptions = listOf("Male", "Female")
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
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
        Text(
            text = "Input your personal info",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = DarkBronze,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                OutlinedTextField(
                    value = userInfoUIState.nick.orEmpty(),
                    onValueChange = { onNickChange(it) },
                    label = { Text("User name") },
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
            }
            item {
                OutlinedTextField(
                    value = userInfoUIState.age.orEmpty(),
                    onValueChange = {
                        onAgeChange(it)
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
                    isError = error != null
                )
            }
            item {
                OutlinedTextField(
                    value = userInfoUIState.height.orEmpty(),
                    onValueChange = { onHeightChange(it) },
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
                    isError = error != null
                )
            }
            item {
                OutlinedTextField(
                    value = userInfoUIState.sex.orEmpty(),
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
                    isError = error != null
                )
            }
            item {
                DropdownMenu(
                    expanded = expanded, onDismissRequest = {
                        expanded = false
                        focusManager.clearFocus()
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    sexOptions.forEach { option ->
                        DropdownMenuItem(text = { Text(text = option) }, onClick = {
                            expanded = false
                            onSexChange(option)
                            focusManager.clearFocus()
                        })
                    }
                }
            }
            item {
                if (error != null) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = error.toString(),
                        color = Color.Red
                    )
                }
                if (isLoading) {
                    ProgressIndicator()
                }
                Button(
                    onClick = {
                        val userId = getUserID()
                        createUserInfo(userId)
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
            }
        }
    }

    if (userInfo != null) {
        navController?.navigate(HOME_ROUTE)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserInfoScreen() {
    UserInfoScreen(
        userInfoUIState = UserInfoUiState(nick = "", age = "", height = "", sex = ""),
        error = null,
        isLoading = false,
        userInfo = null,
        getUserID = { "" },
        createUserInfo = {},
        onNickChange = {},
        onSexChange = {},
        onHeightChange = {},
        onAgeChange = {}
    )
}
