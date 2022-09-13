package com.example.abetterhusbandv2.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.model.User
import com.example.abetterhusbandv2.repository.AccountService
import com.example.abetterhusbandv2.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accService: AccountService,
    private val userRepository: UserRepository
) : ViewModel() {
    fun onAppStart(nextScreen: () -> Unit) {
        if (accService.hasUser()) {
            accService.getCurrentUserId().let { userId ->
                if (userId != "") userRepository.getUserById(userId)
            }
            nextScreen()
        }
        else createAnonymousAccount(nextScreen)
    }

    private fun createAnonymousAccount(nextScreen: () -> Unit) {
        viewModelScope.launch {
            accService.createAnonymousAccount { error, userID ->
                if (error == null && userID != null) {
                    userRepository.addOrUpdateUser(User(userId = userID, listId = ""))
                    nextScreen()
                }
                else Log.e("SplashScreen", "createAnonymousAccount: failed")
            }
        }
    }
}