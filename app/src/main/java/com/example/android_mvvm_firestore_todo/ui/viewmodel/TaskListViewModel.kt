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
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    enum class ReplacePage {
        AddTask,
    }

    private val _replaceEvent = MutableSharedFlow<ReplacePage>()
    val replaceEvent = _replaceEvent.asSharedFlow()

    private val _taskList = MutableLiveData<List<Task>>(listOf())
    val taskList: LiveData<List<Task>> = _taskList

    init {
        getTasks()
    }

    fun onCheckedChanged(task: Task, isDone: Boolean) {
        val newTask = task.copy(isDone = isDone)
        taskRepository.updateTaskStatus(newTask)
    }

    fun onRemoveTask(task: Task) {
        task.documentId?.let {
            taskRepository.removeTask(it)
        }
    }

    fun onClickAddButton() = viewModelScope.launch {
        _replaceEvent.emit(ReplacePage.AddTask)
    }

    private fun getTasks() = viewModelScope.launch {
        taskRepository.getTasks().collect {
            _taskList.value = it
        }
    }
}
