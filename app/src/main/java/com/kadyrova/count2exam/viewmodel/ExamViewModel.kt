package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ExamViewModel : ViewModel() {

    val subject = mutableStateOf("")
    val date = mutableStateOf("")
    val room = mutableStateOf("")
    val notes = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val addSuccess = mutableStateOf(false)

    private val db = FirebaseFirestore.getInstance()

    fun addExam() {
        if (subject.value.isBlank() || date.value.isBlank()) {
            errorMessage.value = "Bitte alle Pflichtfelder ausfüllen"
        }
        isLoading.value = true
        errorMessage.value = null

        val exam = hashMapOf(
            "subject" to subject.value,
            "date" to date.value,
            "room" to room.value,
            "notes" to notes.value
        )

        db.collection("exams")
            .add(exam)
            .addOnSuccessListener {
                isLoading.value = false
                addSuccess.value = true
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "Prüfung konnte nicht gespeichert werden"
            }
    }
}