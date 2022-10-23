package com.example.android_mvvm_firestore_todo.di

import com.example.android_mvvm_firestore_todo.data.source.TaskRepository
import com.example.android_mvvm_firestore_todo.data.source.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskRepositoryModule {

    @Binds
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository
}
