package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.FirebaseTooManyRequestsException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<LoginError?>(null)

    private val auth = FirebaseAuth.getInstance()

    sealed interface LoginEvent {
        object NavigateToHome : LoginEvent
    }

    sealed interface LoginError {
        object EmptyFields : LoginError
        object InvalidCredentials : LoginError
        object NetworkError : LoginError
        object TooManyAttempts : LoginError
        data class Unknown(val message: String?) : LoginError
    }
    private val _events = Channel<LoginEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun login() {
        if (email.value.isBlank() || password.value.isBlank()) {
            error.value = LoginError.EmptyFields
            return
        }
        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email.value, password.value).await()
                _events.send(LoginEvent.NavigateToHome)
            } catch (e: Exception) {
                error.value = mapFirebaseError(e)
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun mapFirebaseError(e: Exception): LoginError = when (e) {
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> LoginError.InvalidCredentials
        is FirebaseNetworkException -> LoginError.NetworkError
        is FirebaseTooManyRequestsException -> LoginError.TooManyAttempts
        else -> LoginError.Unknown(e.message)
    }
}