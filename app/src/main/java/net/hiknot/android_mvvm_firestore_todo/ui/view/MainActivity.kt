package net.hiknot.android_mvvm_firestore_todo.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.hiknot.android_mvvm_firestore_todo.R
import net.hiknot.android_mvvm_firestore_todo.data.source.FirebaseProfileService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseProfile: FirebaseProfileService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (firebaseProfile.isLoggedIn) {
            findNavController(R.id.nav_host).navigate(R.id.action_loginFragment_to_taskListFragment)
        }
    }
}
