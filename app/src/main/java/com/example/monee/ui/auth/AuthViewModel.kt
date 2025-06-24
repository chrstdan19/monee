package com.example.monee.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monee.data.fake.UserStorage
import com.example.monee.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    fun login(email: String, password: String) {
        viewModelScope.launch {
            if (UserStorage.login(email, password)) {
                sessionManager.login(email)
                _loginSuccess.value = true
            } else {
                _loginSuccess.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.logout()
            _loginSuccess.value = false
        }
    }

    fun observeLoginStatus(): Flow<Boolean> = sessionManager.isLoggedIn
}

