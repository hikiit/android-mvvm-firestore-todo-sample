package com.example.android_mvvm_firestore_todo.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_mvvm_firestore_todo.R
import com.example.android_mvvm_firestore_todo.data.source.FirebaseProfileService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseProfile: FirebaseProfileService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = if (firebaseProfile.isLoggedIn) {
                TaskListFragment()
            } else {
                LoginFragment()
            }
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_content, fragment)
                .commit()
        }
    }
}
