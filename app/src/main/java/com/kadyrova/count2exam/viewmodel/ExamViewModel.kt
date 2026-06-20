package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.kadyrova.count2exam.utils.NotificationHelper

class ExamViewModel : ViewModel() {

    val subject = mutableStateOf("")
    val date = mutableStateOf("")
    val room = mutableStateOf("")
    val notes = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val addSuccess = mutableStateOf(false)
    val exams = mutableStateOf<List<Exam>>(emptyList())

    val selectedExam = mutableStateOf<Exam?>(null)
    private val db = FirebaseFirestore.getInstance()

    fun addExam(context: Context) {
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

        db.collection("exams")
            .add(exam)
            .addOnSuccessListener { documentReference ->
                NotificationHelper.scheduleExamReminder(
                    context = context,
                    examId = documentReference.id,
                    examSubject = subject.value,
                    examDate = date.value
                )

                isLoading.value = false
                addSuccess.value = true
            }
            .addOnFailureListener {
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
                    Exam(
                        id = document.id,
                        subject = document.getString("subject") ?: "",
                        date = document.getString("date") ?: "",
                        room = document.getString("room") ?: "",
                        notes = document.getString("notes") ?: ""
                    )
                }
                exams.value = examList
                isLoading.value = false
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "Prüfungen konnten nicht geladen werden"
            }
    }

    fun deleteExam(examId: String) {
        db.collection("exams")
            .document(examId)
            .delete()
            .addOnSuccessListener {
                loadExams()
            }
            .addOnFailureListener {
                errorMessage.value = "Prüfung konnte nicht gelöscht werden"
            }
    }


    fun loadExamById(id: String) {
        isLoading.value = true
        errorMessage.value = null

        db.collection("exams").document(id)
            .get()
            .addOnSuccessListener { document ->
                isLoading.value = false
                if (document.exists()) {
                    selectedExam.value = Exam(
                        id = document.id,
                        subject = document.getString("subject") ?: "",
                        date = document.getString("date") ?: "",
                        room = document.getString("room") ?: "",
                        notes = document.getString("notes") ?: ""
                    )
                } else {
                    errorMessage.value = "Prüfung nicht gefunden"
                }
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = "Prüfung konnte nicht geladen werden"
            }
    }
}

