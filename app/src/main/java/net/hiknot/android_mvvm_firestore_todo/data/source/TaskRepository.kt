package net.hiknot.android_mvvm_firestore_todo.data.source

import kotlinx.coroutines.flow.Flow
import net.hiknot.android_mvvm_firestore_todo.data.Task

interface TaskRepository {

    fun addTask(task: Task)

    fun updateTaskStatus(task: Task)

    fun removeTask(documentId: String)

    suspend fun getTask(documentId: String): Task?

    fun getTasks(): Flow<List<Task>>
}
