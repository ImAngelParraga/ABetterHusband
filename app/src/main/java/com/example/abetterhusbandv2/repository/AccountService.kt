package com.example.abetterhusbandv2.repository

import com.google.firebase.auth.FirebaseUser

interface AccountService {
    fun createAnonymousAccount(onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun hasUser(): Boolean
    fun getCurrentUser(): FirebaseUser?
}