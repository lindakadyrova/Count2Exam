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
    val saveSuccess = mutableStateOf(false)

    private val db = FirebaseFirestore.getInstance()
    fun save(){

        if (subject.value.isBlank() || date.value.isBlank()) {
            errorMessage.value = "Bitte Fach und Datum ausfüllen"
            return
        }

        isLoading.value = true
        errorMessage.value = null

        val examData = hashMapOf(
            "subject" to subject.value,
            "date" to date.value,
            "notes" to notes.value
        )

        db.collection("exams").document()
            .set(examData)
            .addOnSuccessListener {
                isLoading.value = false
                saveSuccess.value = true
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "Speichern fehlgeschlagen"
            }
    }

    fun discard(){
        subject.value = ""
        date.value = ""
        notes.value = ""
        errorMessage.value = null
    }
}