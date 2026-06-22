package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kadyrova.count2exam.R

class PWForgottenViewModel : ViewModel() {
    val email = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val resetSuccess = mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()

    fun resetPassword(context: Context) {
        if (email.value.isBlank()) {
            errorMessage.value =  context.getString(R.string.email_required)
            return
        }
        isLoading.value = true
        errorMessage.value = null

        auth.sendPasswordResetEmail(email.value)
            .addOnSuccessListener {
                isLoading.value = false
                resetSuccess.value = true
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = context.getString(R.string.email_not_found)
            }
    }
}