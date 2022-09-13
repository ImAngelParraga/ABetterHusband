package com.example.abetterhusbandv2.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestoreInstance() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    @Named("HusbandTaskList")
    fun provideHusbandTaskList(
        firestore: FirebaseFirestore
    ) = firestore.collection("Tasks")

    @Provides
    @Singleton
    @Named("Users")
    fun provideUsers(
        firestore: FirebaseFirestore
    ) = firestore.collection("Users")
}