package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kadyrova.count2exam.utils.NotificationHelper
import com.kadyrova.count2exam.R

class EditExamViewModel : ViewModel() {
    val subject = mutableStateOf("")
    val date = mutableStateOf("")
    val notes = mutableStateOf("")
    val room = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val saveSuccess = mutableStateOf(false)

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun loadExam(examId: String, context: Context) {
        val currentUser = auth.currentUser ?: return

        db.collection("exams")
            .document(examId)
            .get()
            .addOnSuccessListener { document ->
                if (document.getString("userId") == currentUser.uid) {
                    subject.value = document.getString("subject") ?: ""
                    date.value = document.getString("date") ?: ""
                    notes.value = document.getString("notes") ?: ""
                    room.value = document.getString("room") ?: ""
                } else {
                    errorMessage.value = context.getString(R.string.error_access_denied)}
            }
            .addOnFailureListener {
                errorMessage.value = context.getString(R.string.error_load_exam)
            }
    }

    fun save(context: Context, examId: String) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            errorMessage.value = context.getString(R.string.error_not_logged_in)
            return
        }

        if (subject.value.isBlank() || date.value.isBlank()) {
            errorMessage.value = context.getString(R.string.error_fill_subject_date)
            return
        }

        isLoading.value = true
        errorMessage.value = null

        val examData = hashMapOf(
            "subject" to subject.value,
            "date" to date.value,
            "notes" to notes.value,
            "userId" to currentUser.uid,
            "room" to room.value
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
                errorMessage.value = context.getString(R.string.error_save_failed)
            }
    }

    fun discard() {
        subject.value = ""
        date.value = ""
        notes.value = ""
        room.value = ""
        errorMessage.value = null
    }
}