package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kadyrova.count2exam.utils.NotificationHelper

class EditExamViewModel : ViewModel() {
    val subject = mutableStateOf("")
    val date = mutableStateOf("")
    val notes = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val saveSuccess = mutableStateOf(false)

    private val db = FirebaseFirestore.getInstance()
    fun loadExam(examId: String) {
        db.collection("exams")
            .document(examId)
            .get()
            .addOnSuccessListener { document ->
                subject.value = document.getString("subject") ?: ""
                date.value = document.getString("date") ?: ""
                notes.value = document.getString("notes") ?: ""
            }
            .addOnFailureListener {
                errorMessage.value = "Prüfung konnte nicht geladen werden"
            }
    }

    fun save(context: Context, examId: String) {

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

        db.collection("exams").document(examId)
            .set(examData)
            .addOnSuccessListener {
                NotificationHelper.scheduleExamReminder(
                    context = context,
                    examId = examId,
                    examSubject = subject.value,
                    examDate = date.value
                )
                isLoading.value = false
                saveSuccess.value = true
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "Speichern fehlgeschlagen"
            }
    }

    fun discard() {
        subject.value = ""
        date.value = ""
        notes.value = ""
        errorMessage.value = null
    }
}