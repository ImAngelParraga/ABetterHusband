package com.example.abetterhusbandv2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.model.LoginUiState
import com.example.abetterhusbandv2.repository.AccountService
import com.example.abetterhusbandv2.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel()  {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState>
        get() = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }
}