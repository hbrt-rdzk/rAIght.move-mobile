package com.raightmove.raightmove.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.raightmove.raightmove.models.Training
import com.raightmove.raightmove.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

const val USERS_COLLECTION = "users"
const val TRAININGS_COLLECTION = "trainings"

class FirebaseFirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun addUserToDb(
        userId: String,
        user: User,
    ) = withContext(Dispatchers.IO) {
        db.collection(USERS_COLLECTION).document(userId).set(user).addOnSuccessListener {
            Log.d("Firestore", "User added/updated successfully")
        }.addOnFailureListener { e ->
            Log.w("Firestore", "Error adding/updating user", e)
        }
    }

    suspend fun getTrainingIds(userID: String): List<String> = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            db.collection(USERS_COLLECTION).document(userID).get()
                .addOnSuccessListener { document ->
                    val trainings = document.get("trainings")
                    if (trainings is List<*>) {
                        val trainingIds = trainings.filterIsInstance<String>()
                        continuation.resume(trainingIds)
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    suspend fun getTrainings(trainingIds: List<String>): List<Training> =
        withContext(Dispatchers.IO) {
            val trainings = mutableListOf<Training>()

            suspendCancellableCoroutine { continuation ->
                trainingIds.forEach { id ->
                    db.collection(TRAININGS_COLLECTION).document(id).get()
                        .addOnSuccessListener { document ->
                            if (document != null && document.exists()) {
                                val training = document.toObject(Training::class.java)
                                if (training != null) {
                                    trainings.add(training)
                                }
                            }
                        }.addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
            }
        }

    suspend fun checkIfExistsInDB(userId: String): Boolean = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            db.collection(USERS_COLLECTION).document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    suspend fun getUserInfo(userId: String): User? = withContext(Dispatchers.IO) {
        return@withContext try {
            val documentSnapshot = db.collection(USERS_COLLECTION).document(userId).get().await()
            documentSnapshot.toObject<User>()
        } catch (e: Exception) {
            Log.w("Firestore", "Error getting user", e)
            null
        }
    }

    suspend fun addTrainingToDb(training: Training): String = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            db.collection(TRAININGS_COLLECTION).add(training)
                .addOnSuccessListener { documentReference ->
                    val trainingId = documentReference.id
                    Log.d(TAG, "DocumentSnapshot added with ID: $trainingId")
                    continuation.resume(trainingId)
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    continuation.resumeWithException(e)
                }

        }
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