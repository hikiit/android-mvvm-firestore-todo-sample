package com.example.android_mvvm_firestore_todo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mvvm_firestore_todo.data.Task
import com.example.android_mvvm_firestore_todo.data.source.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    enum class ReplacePage {
        TaskList,
    }

    private val _replaceEvent = MutableSharedFlow<ReplacePage>()
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
        _replaceEvent.emit(ReplacePage.TaskList)
    }
}
