package com.raightmove.raightmove.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raightmove.raightmove.models.Training
import com.raightmove.raightmove.models.User
import com.raightmove.raightmove.repositories.FirebaseFirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val repository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _userInfo = MutableStateFlow<User?>(null)
    private val _userTrainings = MutableStateFlow<List<Training>?>(null)

    val isLoading: StateFlow<Boolean> = _isLoading
    val error: StateFlow<String?> = _error
    val userInfo: StateFlow<User?> = _userInfo
    val userTrainings: StateFlow<List<Training>?> = _userTrainings

    var userInfoUiState by mutableStateOf(UserInfoUiState())
        private set

    fun onNickChange(nick: String) {
        userInfoUiState = userInfoUiState.copy(nick = nick)
    }

    fun onAgeChange(age: String) {
        userInfoUiState = userInfoUiState.copy(age = age)
    }

    fun onSexChange(sex: String) {
        userInfoUiState = userInfoUiState.copy(sex = sex)
    }

    fun onHeightChange(height: String) {
        userInfoUiState = userInfoUiState.copy(height = height)
    }

    suspend fun fetchUserInfo(userID: String) {
        try {
            _isLoading.value = true
            _error.value = null
            _userInfo.value = repository.getUserInfo(userID)
        } catch (e: Exception) {
            _error.value = e.localizedMessage
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun fetchTrainings(userID: String) {
        try {
            _isLoading.value = true
            _error.value = null

            val trainingIDs = repository.getTrainingIds(userID)
            val trainings = repository.getTrainings(trainingIDs)
            _userTrainings.value = trainings
        } catch (e: Exception) {
            _error.value = e.localizedMessage
        } finally {
            _isLoading.value = false
        }
    }

    fun addUserInfo(context: Context, userID: String) = viewModelScope.launch {
        try {
            _isLoading.value = true
            _error.value = null

            val user = userInfoUiState.getUser()
            repository.addUserToDb(userID, user)
            Toast.makeText(context, "Successfully added user", Toast.LENGTH_SHORT).show()
            _userInfo.value = user
        } catch (e: Exception) {
            _error.value = e.localizedMessage
            Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
        } finally {
            _isLoading.value = false
        }
    }

    fun addTraining(context: Context, userID: String, training: Training) = viewModelScope.launch {
        try {
            _isLoading.value = true
            _error.value = null

            val trainingID = repository.addTrainingToDb(training)
            repository.updateUserTrainings(userID, trainingID)
            Toast.makeText(context, "Successfully added training", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            _error.value = e.localizedMessage
            Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
        } finally {
            _isLoading.value = false
        }
    }
}

data class UserInfoUiState(
    val nick: String? = null,
    val age: String? = null,
    val sex: String? = null,
    val height: String? = null,
) {
    private fun validateUserInfo(): Boolean {
        return age != null && sex != null && height != null && nick != null
    }

    fun getUser(): User {
        if (validateUserInfo()) {
            return User(
                nick = nick!!,
                age = age!!.toInt(),
                gender = sex!!,
                height = height!!.toInt(),
                trainings = mutableListOf()
            )
        } else {
            throw IllegalArgumentException(
                "All parameters must be set"
            )
        }

    }
}