package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class EditExamViewModel : ViewModel() {
    val subject = mutableStateOf("")
    val date = mutableStateOf("")
    val notes = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val db = FirebaseFirestore.getInstance()
    fun save(){
        isLoading.value = true

        val examData = hashMapOf(
            "subject" to subject.value,
            "date" to date.value,
            "notes" to notes.value
        )

        db.collection("exams").document()
            .set(examData)
            .addOnSuccessListener {
                isLoading.value = false
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "Speichern fehlgeschlagen"
            }
    }
}