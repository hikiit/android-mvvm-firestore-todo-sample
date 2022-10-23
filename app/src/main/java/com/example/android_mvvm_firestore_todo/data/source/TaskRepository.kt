package com.example.android_mvvm_firestore_todo.data.source

import com.example.android_mvvm_firestore_todo.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun addTask(task: Task)

    fun updateTaskStatus(task: Task)

    fun removeTask(documentId: String)

    suspend fun getTask(documentId: String): Task?

    fun getTasks(): Flow<List<Task>>
}
