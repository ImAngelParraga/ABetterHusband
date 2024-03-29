package com.example.abetterhusbandv2.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AccountService {

    override fun createAnonymousAccount(onResult: (Throwable?, String?) -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener { onResult(it.exception, it.result.user?.uid) }
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun hasUser(): Boolean {
        return auth.currentUser != null
    }

    override fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: ""
    }
}