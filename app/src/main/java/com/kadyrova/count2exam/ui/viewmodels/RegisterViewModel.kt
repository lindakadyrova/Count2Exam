package com.kadyrova.count2exam.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel : ViewModel() {

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val registerSuccess = mutableStateOf(false)

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun register() {
        if (password.value != confirmPassword.value) {
            errorMessage.value = "Passwörter stimmen nicht überein"
            return
        }

        isLoading.value = true
        errorMessage.value = null

        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnSuccessListener { result ->
                val uid = result.user!!.uid

                val user = hashMapOf(
                    "firstName" to firstName.value,
                    "lastName" to lastName.value,
                    "username" to username.value,
                    "email" to email.value
                )

                db.collection("users").document(uid).set(user)
                    .addOnSuccessListener {
                        isLoading.value = false
                        registerSuccess.value = true
                    }
                    .addOnFailureListener { e ->
                        isLoading.value = false
                        errorMessage.value = e.message
                    }
            }
            .addOnFailureListener { e ->
                isLoading.value = false
                errorMessage.value = e.message
            }
    }
}