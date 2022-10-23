package net.hiknot.android_mvvm_firestore_todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.hiknot.android_mvvm_firestore_todo.data.source.FirebaseProfileService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseProfile: FirebaseProfileService
) : ViewModel() {

    enum class ReplacePage {
        TaskList,
    }

    private val _replaceEvent = MutableSharedFlow<ReplacePage>()
    val replaceEvent = _replaceEvent.asSharedFlow()

    fun signInGuest() = viewModelScope.launch {
        firebaseProfile.signInAnonymously()
        _replaceEvent.emit(ReplacePage.TaskList)
    }
}
