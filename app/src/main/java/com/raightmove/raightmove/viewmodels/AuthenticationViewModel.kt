package com.raightmove.raightmove.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.raightmove.raightmove.repositories.FirebaseAuthenticationRepository
import kotlinx.coroutines.launch


const val WEB_CLIENT_ID = "870711698871-j35148ohje94kr0ourq2lh15pg6ur9oi.apps.googleusercontent.com"

class AuthenticationViewModel(
    private val repository: FirebaseAuthenticationRepository = FirebaseAuthenticationRepository()
) : ViewModel() {
    val currentUser = repository.currentUser

    val hasUser get() = repository.hasUser()

    val getUserId get() = repository.getUserID()

    var loginUIState by mutableStateOf(LoginUiState())
        private set

    fun onUserNameChange(userName: String) {
        loginUIState = loginUIState.copy(userName = userName)
    }

    fun onPasswordChange(password: String) {
        loginUIState = loginUIState.copy(password = password)
    }

    fun onUserNameSignUp(userNameSignUp: String) {
        loginUIState = loginUIState.copy(userNameSignUp = userNameSignUp)
    }

    fun onPasswordSignUp(passwordSignUp: String) {
        loginUIState = loginUIState.copy(passwordSignUp = passwordSignUp)
    }

    fun onConfirmPasswordSignUp(confirmPasswordSignUp: String) {
        loginUIState = loginUIState.copy(confirmPasswordSignUp = confirmPasswordSignUp)
    }

    private fun validateLoginForm() =
        loginUIState.userName.isBlank() && loginUIState.password.isBlank()

    private fun validateSignUpForm() =
        loginUIState.userNameSignUp.isBlank() && loginUIState.passwordSignUp.isBlank() && loginUIState.confirmPasswordSignUp.isBlank()

    private fun checkIfSuccessfulLogin(isSuccessLogin: Boolean, context: Context) {
        if (isSuccessLogin) {
            Toast.makeText(context, "Success logging", Toast.LENGTH_SHORT).show()
            loginUIState = loginUIState.copy(isSuccessLogin = true)
        } else {
            Toast.makeText(context, "Failed logging", Toast.LENGTH_SHORT).show()
            loginUIState = loginUIState.copy(isSuccessLogin = false)
        }
    }

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (validateSignUpForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUIState = loginUIState.copy(isLoading = true)
            if (loginUIState.passwordSignUp != loginUIState.confirmPasswordSignUp) {
                throw IllegalArgumentException(
                    "Passwords do not match"
                )
            }
            loginUIState = loginUIState.copy(signUpError = null)
            repository.createUser(
                loginUIState.userNameSignUp,
                loginUIState.passwordSignUp
            ) { isSuccessfulLogin ->
                checkIfSuccessfulLogin(isSuccessfulLogin, context)
            }
        } catch (e: Exception) {
            loginUIState = loginUIState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUIState = loginUIState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (validateLoginForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUIState = loginUIState.copy(isLoading = true)
            loginUIState = loginUIState.copy(loginError = null)
            repository.login(loginUIState.userName, loginUIState.password) { isSuccessfulLogin ->
                checkIfSuccessfulLogin(isSuccessfulLogin, context)
            }
        } catch (e: Exception) {
            loginUIState = loginUIState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUIState = loginUIState.copy(isLoading = false)
        }
    }

    fun loginUserByGoogle(context: Context) = viewModelScope.launch {
        try {
            val credentialManager = CredentialManager.create(context)

            loginUIState = loginUIState.copy(isLoading = true)
            loginUIState = loginUIState.copy(loginError = null)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(WEB_CLIENT_ID).build()

            val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
            val credentialResult = credentialManager.getCredential(context, request)

            repository.loginByGoogle(credentialResult.credential) { isSuccessfulLogin ->
                checkIfSuccessfulLogin(isSuccessfulLogin, context)
            }
        } catch (e: Exception) {
            loginUIState = loginUIState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUIState = loginUIState.copy(isLoading = false)
        }
    }
}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val userNameSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null
)