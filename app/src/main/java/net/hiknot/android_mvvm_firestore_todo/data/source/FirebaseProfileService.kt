package net.hiknot.android_mvvm_firestore_todo.data.source

interface FirebaseProfileService {

    val isLoggedIn: Boolean

    val uid: String?

    suspend fun signInAnonymously()

    suspend fun delete()
}