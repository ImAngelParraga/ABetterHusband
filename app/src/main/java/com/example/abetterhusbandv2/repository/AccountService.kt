package com.example.abetterhusbandv2.repository

interface AccountService {
    fun createAnonymousAccount(onResult: (Throwable?, String?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun hasUser(): Boolean
    fun getCurrentUserId(): String
}