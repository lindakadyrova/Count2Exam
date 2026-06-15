package com.kadyrova.count2exam.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class PWForgottenViewModel : ViewModel() {
    val email = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val resetSuccess = mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()

    fun resetPassword() {
        isLoading.value = true
        errorMessage.value = null

        auth.sendPasswordResetEmail(email.value)
            .addOnSuccessListener {
                isLoading.value = false
                resetSuccess.value = true
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "E-Mail konnte nicht gefunden werden"
            }
    }
}