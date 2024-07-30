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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


const val WEB_CLIENT_ID = "870711698871-j35148ohje94kr0ourq2lh15pg6ur9oi.apps.googleusercontent.com"

class AuthenticationViewModel(
    private val repository: FirebaseAuthenticationRepository = FirebaseAuthenticationRepository()
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _isSuccessLogin = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val isLoading: StateFlow<Boolean> = _isLoading
    val isSuccessLogin: StateFlow<Boolean> = _isSuccessLogin
    val error: StateFlow<String?> = _error

    val currentUser = repository.currentUser

    val hasUser get() = repository.hasUser()

    val userId get() = repository.getUserID()

    var loginUIState by mutableStateOf(LoginUiState())
        private set

    fun resetState() {
        loginUIState = LoginUiState()
        _error.value = null
        _isSuccessLogin.value = false
        _isLoading.value = false
    }

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
            _isSuccessLogin.value = true
        } else {
            Toast.makeText(context, "Failed logging", Toast.LENGTH_SHORT).show()
            _isSuccessLogin.value = false
        }
    }

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            _error.value = null
            _isLoading.value = true

            if (validateSignUpForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            if (loginUIState.passwordSignUp != loginUIState.confirmPasswordSignUp) {
                throw IllegalArgumentException(
                    "Passwords do not match"
                )
            }
            repository.createUser(
                loginUIState.userNameSignUp, loginUIState.passwordSignUp
            ) { isSuccessfulLogin ->
                checkIfSuccessfulLogin(isSuccessfulLogin, context)
            }
            _isSuccessLogin.value = true
        } catch (e: Exception) {
            _isSuccessLogin.value = false
            _error.value = e.localizedMessage
        } finally {
            _isLoading.value = false
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            _error.value = null
            _isLoading.value = true

            if (validateLoginForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            repository.login(loginUIState.userName, loginUIState.password) { isSuccessfulLogin ->
                checkIfSuccessfulLogin(isSuccessfulLogin, context)
            }
            _isSuccessLogin.value = true
        } catch (e: Exception) {
            _isSuccessLogin.value = false
            _error.value = e.localizedMessage
        } finally {
            _isLoading.value = false
        }
    }

    fun loginUserByGoogle(context: Context) = viewModelScope.launch {
        try {
            _error.value = null
            _isLoading.value = true

            val credentialManager = CredentialManager.create(context)
            val googleIdOption = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(true)
                .setServerClientId(WEB_CLIENT_ID).build()

            val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
            val credentialResult = credentialManager.getCredential(context, request)

            repository.loginByGoogle(credentialResult.credential) { isSuccessfulLogin ->
                checkIfSuccessfulLogin(isSuccessfulLogin, context)
            }
            _isSuccessLogin.value = true
        } catch (e: Exception) {
            _isSuccessLogin.value = false
            _error.value = e.localizedMessage
        } finally {
            _isLoading.value = false
        }
    }
}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val userNameSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
)