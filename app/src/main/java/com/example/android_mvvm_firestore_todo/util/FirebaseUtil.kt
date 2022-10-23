package com.example.android_mvvm_firestore_todo.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtil {
    fun getFirebaseFirestoreInstance(): FirebaseFirestore {
        return if (BuildConfig.DEBUG) {
            FirebaseFirestore.getInstance() // TODO Return emulator
        } else {
            FirebaseFirestore.getInstance()
        }
    }

    fun getFirebaseAuthInstance(): FirebaseAuth {
        return if (BuildConfig.DEBUG) {
            FirebaseAuth.getInstance()
        } else {
            FirebaseAuth.getInstance()
        }
    }
}
