package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.State


class PWForgottenViewModel @JvmOverloads constructor(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    fun onEmailChange(value: String) { _email.value = value }

    val isLoading = mutableStateOf(false)

    sealed interface ResetError {
        object EmptyEmail : ResetError
        object InvalidEmailFormat : ResetError
        object NetworkError : ResetError
        data class Unknown(val message: String?) : ResetError
    }
    val error = mutableStateOf<ResetError?>(null)
    val resetSuccess = mutableStateOf(false)

    sealed interface ResetEvent {
        object EmailSent : ResetEvent
    }

    private val _events = Channel<ResetEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun resetPassword() {
        if (email.value.isBlank()) {
            error.value = ResetError.EmptyEmail
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            error.value = ResetError.InvalidEmailFormat
            return
        }

        isLoading.value = true
        error.value = null

        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            try {
                auth.sendPasswordResetEmail(email.value).await()
                resetSuccess.value = true
                _events.send(ResetEvent.EmailSent)
            } catch (e: Exception) {
                if (e is FirebaseAuthInvalidUserException) {
                    resetSuccess.value = true
                    _events.send(ResetEvent.EmailSent)
                } else {
                    error.value = mapFirebaseError(e)
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun mapFirebaseError(e: Exception): ResetError = when (e) {
        is FirebaseNetworkException -> ResetError.NetworkError
        else -> ResetError.Unknown(e.message)
    }
}