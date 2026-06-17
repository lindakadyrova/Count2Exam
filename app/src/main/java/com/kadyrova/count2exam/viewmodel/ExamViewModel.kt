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

    val exams = mutableStateOf<List<Map<String, Any>>>(emptyList())
    private val db = FirebaseFirestore.getInstance()

    fun addExam() {
        if (subject.value.isBlank() || date.value.isBlank()) {
            errorMessage.value = "Bitte alle Pflichtfelder ausfüllen"
            return
        }
        isLoading.value = true
        errorMessage.value = null

        val exam = hashMapOf(
            "subject" to subject.value,
            "date" to date.value,
            "room" to room.value,
            "notes" to notes.value
        )

        db.collection("exams").add(exam).addOnSuccessListener {
            isLoading.value = false
            addSuccess.value = true
        }.addOnFailureListener {
            isLoading.value = false
            errorMessage.value = "Prüfung konnte nicht gespeichert werden"
        }
    }

    fun clearFields() {
        subject.value = ""
        date.value = ""
        room.value = ""
        notes.value = ""

        errorMessage.value = null
        addSuccess.value = false
    }

    fun loadExams() {
        isLoading.value = true
        errorMessage.value = null

        db.collection("exams")
            .get()
            .addOnSuccessListener { result ->
                val examList = result.documents.map { document ->
                    document.data ?: emptyMap()
                }

                exams.value = examList
                isLoading.value = false
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "Prüfungen konnten nicht geladen werden"
            }
    }
}

