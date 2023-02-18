package net.hiknot.android_mvvm_firestore_todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.hiknot.android_mvvm_firestore_todo.data.source.FirebaseProfileService
import net.hiknot.android_mvvm_firestore_todo.ui.entity.Page
import net.hiknot.android_mvvm_firestore_todo.util.Event
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseProfile: FirebaseProfileService
) : ViewModel() {

    private val _replaceEvent = MutableSharedFlow<Event<Page>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val replaceEvent = _replaceEvent.asSharedFlow()

    // 匿名ログインする
    fun signInGuest() = viewModelScope.launch {
        firebaseProfile.signInAnonymously()
        _replaceEvent.emit(Event(Page.TaskList))
    }
}
