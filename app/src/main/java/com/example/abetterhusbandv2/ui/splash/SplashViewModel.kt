package com.example.abetterhusbandv2.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.repository.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accService: AccountService
) : ViewModel() {
    fun onAppStart(nextScreen: () -> Unit) {
        if (accService.hasUser()) nextScreen()
        else createAnonymousAccount(nextScreen)
    }

    private fun createAnonymousAccount(nextScreen: () -> Unit) {
        viewModelScope.launch {
            accService.createAnonymousAccount { error ->
                if (error == null) nextScreen()
                else Log.e("SplashScreen", "createAnonymousAccount: failed")
            }
        }
    }
}