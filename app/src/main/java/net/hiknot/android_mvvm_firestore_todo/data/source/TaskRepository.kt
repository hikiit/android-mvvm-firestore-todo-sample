package net.hiknot.android_mvvm_firestore_todo.data.source

import kotlinx.coroutines.flow.Flow
import net.hiknot.android_mvvm_firestore_todo.data.Task

interface TaskRepository {

    fun addTask(task: Task)

    fun updateTaskStatus(task: Task)

    fun removeTask(id: String)

    suspend fun getTask(id: String): Task?

    fun getTasks(): Flow<List<Task>>
}
