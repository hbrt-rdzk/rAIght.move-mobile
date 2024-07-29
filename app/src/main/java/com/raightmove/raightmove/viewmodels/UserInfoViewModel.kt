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
import kotlinx.coroutines.launch

class UserInfoViewModel(
    private val repository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
) : ViewModel() {
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

    suspend fun userExistsInDB(userID: String): Boolean {
        try {
            val isExist = repository.checkIfExistsInDB(userID)
            return isExist
        } catch (e: Exception) {
            userInfoUiState = userInfoUiState.copy(error = e.localizedMessage)
            return false
        }
    }

    suspend fun getTrainings(userID: String): List<Training> {
        val trainingIDs = repository.getTrainingIds(userID)
        return repository.getTrainings(trainingIDs)
    }

    fun addUser(context: Context, userID: String) = viewModelScope.launch {
        try {
            userInfoUiState = userInfoUiState.copy(isLoading = true)
            userInfoUiState = userInfoUiState.copy(error = null)

            val user = userInfoUiState.getUser()
            repository.addUserToDb(userID, user)
            userInfoUiState = userInfoUiState.copy(isSuccessfullyAdded = true)
            Toast.makeText(context, "Successfully added user", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            userInfoUiState = userInfoUiState.copy(error = e.localizedMessage)
            userInfoUiState = userInfoUiState.copy(isSuccessfullyAdded = false)
            e.printStackTrace()
        } finally {
            userInfoUiState = userInfoUiState.copy(isLoading = false)
        }
    }

    fun addTraining(context: Context, userID: String, training: Training) = viewModelScope.launch {
        try {
            userInfoUiState = userInfoUiState.copy(isLoading = true)

            userInfoUiState = userInfoUiState.copy(error = null)

            val trainingID = repository.addTrainingToDb(training)
            repository.updateUserTrainings(userID, trainingID)
            Toast.makeText(context, "Successfully added training", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            userInfoUiState = userInfoUiState.copy(error = e.localizedMessage)
            e.printStackTrace()
        } finally {
            userInfoUiState = userInfoUiState.copy(isLoading = false)
        }
    }
}

data class UserInfoUiState(
    val nick: String? = null,
    val age: String? = null,
    val sex: String? = null,
    val height: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val isSuccessfullyAdded: Boolean = false
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
                height = height!!.toInt()
            )
        } else {
            throw IllegalArgumentException(
                "All parameters must be set"
            )
        }

    }
}