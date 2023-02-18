package net.hiknot.android_mvvm_firestore_todo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.hiknot.android_mvvm_firestore_todo.data.Task
import net.hiknot.android_mvvm_firestore_todo.data.source.TaskRepository
import net.hiknot.android_mvvm_firestore_todo.ui.entity.Page
import net.hiknot.android_mvvm_firestore_todo.util.Event
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _replaceEvent = MutableSharedFlow<Event<Page>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val replaceEvent = _replaceEvent.asSharedFlow()

    private val _taskTitle = MutableLiveData("")
    val taskTitle: LiveData<String> = _taskTitle

    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun onChangedTextTitle(text: CharSequence) = viewModelScope.launch {
        val title = text.toString()
        _taskTitle.value = title
        _isButtonEnabled.value = title.isNotBlank()
    }

    fun onClickAddButton() = viewModelScope.launch {
        val newTask = Task(title = taskTitle.value!!, isDone = false)
        taskRepository.addTask(newTask)
        _replaceEvent.emit(Event(Page.TaskList))
    }
}
