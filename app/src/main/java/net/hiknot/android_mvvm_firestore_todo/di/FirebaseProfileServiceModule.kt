package net.hiknot.android_mvvm_firestore_todo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.hiknot.android_mvvm_firestore_todo.data.source.FirebaseProfileService
import net.hiknot.android_mvvm_firestore_todo.data.source.FirebaseProfileServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseProfileServiceModule {

    @Binds
    abstract fun bindFirebaseProfileServiceModule(
        firebaseProfileServiceImpl: FirebaseProfileServiceImpl,
    ): FirebaseProfileService
}
