package com.example.android_mvvm_firestore_todo.data.source

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseProfileServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    // private val firestore: FirebaseFirestore, // Create documentation for User if necessary
) : FirebaseProfileService {

    companion object {
        private const val TAG = "FirebaseProfileService"
    }

    override val isLoggedIn: Boolean
        get() = firebaseAuth.currentUser != null

    override val uid: String?
        get() = firebaseAuth.uid

    override suspend fun signInAnonymously() {
        val authResult = firebaseAuth.signInAnonymously().await()

        if (authResult.user == null) {
            Log.e(TAG, "FirebaseAuth User is null")
        } else {
            /* Create a document for User if necessary */
        }
    }

    override suspend fun delete() {
        // User data deletion is performed by FirestoreExtension
        firebaseAuth.currentUser?.delete()?.await()
    }
}
