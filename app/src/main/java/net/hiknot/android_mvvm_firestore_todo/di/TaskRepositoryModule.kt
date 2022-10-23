package net.hiknot.android_mvvm_firestore_todo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.hiknot.android_mvvm_firestore_todo.data.source.TaskRepository
import net.hiknot.android_mvvm_firestore_todo.data.source.TaskRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskRepositoryModule {

    @Binds
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository
}
