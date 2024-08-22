package com.raightmove.raightmove.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.raightmove.raightmove.models.Training
import com.raightmove.raightmove.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val USERS_COLLECTION = "users"
const val TRAININGS_COLLECTION = "trainings"

class FirebaseFirestoreRepository {
    val db = FirebaseFirestore.getInstance()

    suspend fun addUserToDb(
        userId: String,
        user: User,
    ) = withContext(Dispatchers.IO) {
        db.collection(USERS_COLLECTION).document(userId).set(user).await()
    }

    suspend fun getTrainingIds(userID: String): List<String> = withContext(Dispatchers.IO) {
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("users").document(userID).get().await()
        val trainings = document.get("trainings")
        if (trainings is List<*>) {
            trainings.filterIsInstance<String>()
        } else {
            emptyList()
        }
    }


    suspend fun getTrainings(trainingIds: List<String>): List<Training> =
        withContext(Dispatchers.IO) {
            val trainings = mutableListOf<Training>()
            trainingIds.forEach { id ->
                val document = db.collection(TRAININGS_COLLECTION).document(id).get().await()
                val training = document.toObject(Training::class.java)
                if (training != null) {
                    trainings.add(training)
                }
            }
            trainings
        }

    suspend fun getUserInfo(userId: String): User? = withContext(Dispatchers.IO) {
        val documentSnapshot = db.collection(USERS_COLLECTION).document(userId).get().await()
        if (documentSnapshot.exists()) {
            documentSnapshot.toObject<User>()
        } else {
            null
        }
    }

    suspend fun addTrainingToDb(training: Training): String = withContext(Dispatchers.IO) {
        val document = db.collection(TRAININGS_COLLECTION).add(training).await()
        val trainingId = document.id
        trainingId
    }

    suspend fun updateUserTrainings(userId: String, trainingId: String) =
        withContext(Dispatchers.IO) {
            val userRef = db.collection(USERS_COLLECTION).document(userId)

            userRef.update("trainings", FieldValue.arrayUnion(trainingId)).addOnSuccessListener {
                Log.d(TAG, "Updated successfully")
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        }
}