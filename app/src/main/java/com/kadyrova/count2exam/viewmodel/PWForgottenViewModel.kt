package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


class PWForgottenViewModel : ViewModel() {
    val email = mutableStateOf("")

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


    private val auth = FirebaseAuth.getInstance()

    // PWForgottenViewModel.kt
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

        auth.sendPasswordResetEmail(email.value)
            .addOnSuccessListener {
                isLoading.value = false
                resetSuccess.value = true
                _events.trySend(ResetEvent.EmailSent)
            }
            .addOnFailureListener { e ->
                isLoading.value = false
                if (e is FirebaseAuthInvalidUserException) {
                    resetSuccess.value = true
                    _events.trySend(ResetEvent.EmailSent)
                } else {
                    error.value = mapFirebaseError(e)
                }
            }
    }

    private fun mapFirebaseError(e: Exception): ResetError = when (e) {
        is FirebaseNetworkException -> ResetError.NetworkError
        else -> ResetError.Unknown(e.message)
    }
}