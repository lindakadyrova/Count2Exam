package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class RegisterViewModel : ViewModel() {

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")

    val isLoading = mutableStateOf(false)

    sealed interface RegisterError {
        object EmptyFields : RegisterError
        object PasswordsDoNotMatch : RegisterError
        data class Unknown(val message: String?) : RegisterError
    }

    val error = mutableStateOf<RegisterError?>(null)

    sealed interface RegisterEvent {
        object NavigateToHome : RegisterEvent
    }

    private val _events = Channel<RegisterEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun register() {
        if (
            firstName.value.isBlank() ||
            lastName.value.isBlank() ||
            username.value.isBlank() ||
            email.value.isBlank() ||
            password.value.isBlank() ||
            confirmPassword.value.isBlank()
        ) {
            error.value = RegisterError.EmptyFields
            return
        }

        if (password.value != confirmPassword.value) {
            error.value = RegisterError.PasswordsDoNotMatch
            return
        }

        isLoading.value = true
        error.value = null

        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid == null) {
                    isLoading.value = false
                    error.value = RegisterError.Unknown(null)
                    return@addOnSuccessListener
                }

                val user = hashMapOf(
                    "firstName" to firstName.value,
                    "lastName" to lastName.value,
                    "username" to username.value,
                    "email" to email.value
                )

                db.collection("users").document(uid).set(user)
                    .addOnSuccessListener {
                        isLoading.value = false
                        _events.trySend(RegisterEvent.NavigateToHome)
                    }
                    .addOnFailureListener { e ->
                        result.user?.delete()
                        isLoading.value = false
                        error.value = RegisterError.Unknown(e.message)
                    }
            }
            .addOnFailureListener { e ->
                isLoading.value = false
                error.value = RegisterError.Unknown(e.message)
            }
    }
}