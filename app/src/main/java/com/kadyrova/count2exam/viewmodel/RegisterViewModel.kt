package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kadyrova.count2exam.R

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

    fun register(context: Context) {
        if (
            username.value.isBlank() ||
            email.value.isBlank() ||
            password.value.isBlank() ||
            confirmPassword.value.isBlank()
        ) {
            errorMessage.value = context.getString(R.string.fill_all_fields)
            return
        }

        if (password.value != confirmPassword.value) {
            errorMessage.value = context.getString(R.string.passwords_no_match)
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