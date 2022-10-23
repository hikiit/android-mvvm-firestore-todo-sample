package com.example.android_mvvm_firestore_todo.di

import com.example.android_mvvm_firestore_todo.data.source.FirebaseProfileService
import com.example.android_mvvm_firestore_todo.data.source.FirebaseProfileServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseProfileServiceModule {

    @Binds
    abstract fun bindFirebaseProfileServiceModule(
        firebaseProfileServiceImpl: FirebaseProfileServiceImpl,
    ): FirebaseProfileService
}
