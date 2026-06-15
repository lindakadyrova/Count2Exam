package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val loginSuccess = mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()

    fun login() {
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Bitte alle Felder ausfüllen"
            return
        }
        isLoading.value = true
        errorMessage.value = null

        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnSuccessListener {
                isLoading.value = false
                loginSuccess.value = true
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "E-Mail oder Passwort ist falsch"
            }
    }
}