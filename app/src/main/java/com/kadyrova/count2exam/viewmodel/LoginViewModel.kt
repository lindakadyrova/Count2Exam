package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.FirebaseTooManyRequestsException
import com.kadyrova.count2exam.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val auth = FirebaseAuth.getInstance()

    sealed interface LoginEvent {
        object NavigateToHome : LoginEvent
    }
    private val _events = Channel<LoginEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun login(context: Context) {
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = context.getString(R.string.fill_all_fields)
            return
        }
        isLoading.value = true
        errorMessage.value = null

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email.value, password.value).await()
                _events.send(LoginEvent.NavigateToHome)
            } catch (e: Exception) {
                errorMessage.value = mapFirebaseError(context, e)
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun mapFirebaseError(context: Context, e: Exception): String = when (e) {
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> context.getString(R.string.wrong_credentials)
        is FirebaseNetworkException -> context.getString(R.string.no_internet)
        is FirebaseTooManyRequestsException -> context.getString(R.string.too_many_attempts)
        else -> context.getString(R.string.unknown_error)
    }
}