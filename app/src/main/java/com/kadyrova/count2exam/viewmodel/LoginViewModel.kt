package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.kadyrova.count2exam.R

class LoginViewModel : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val loginSuccess = mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()

    fun login(context: Context) {
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = context.getString(R.string.fill_all_fields)
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
                errorMessage.value = context.getString(R.string.wrong_credentials)
            }
    }
}