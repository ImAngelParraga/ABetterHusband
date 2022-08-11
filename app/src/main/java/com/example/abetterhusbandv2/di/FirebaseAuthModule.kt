package com.example.abetterhusbandv2.di

import com.example.abetterhusbandv2.repository.AccountService
import com.example.abetterhusbandv2.repository.AccountServiceImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseAuthModule {

    @Binds
    abstract fun bindAccountService(
        accountServiceImpl: AccountServiceImpl
    ): AccountService

    companion object {
        @Provides
        @Singleton
        fun provideAuthInstance() = FirebaseAuth.getInstance()
    }

}