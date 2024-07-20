package com.raightmove.raightmove.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.raightmove.raightmove.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val USERS_COLLECTION = "users"

class FirebaseFirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun addUserToDb(
        userId: String,
        user: User,
    ) = withContext(Dispatchers.IO) {
        db.collection(USERS_COLLECTION)
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("Firestore", "User added/updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding/updating user", e)
            }

    }

    suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
        return@withContext try {
            val documentSnapshot = db.collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .await()
            documentSnapshot.toObject<User>()
        } catch (e: Exception) {
            Log.w("Firestore", "Error getting user", e)
            null
        }
    }
}