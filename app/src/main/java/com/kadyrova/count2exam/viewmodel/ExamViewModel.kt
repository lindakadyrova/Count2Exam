package com.kadyrova.count2exam.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kadyrova.count2exam.R
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

    // KI-Unterstützung: selectedExam wurde mit KI-Hilfe ergänzt,
    // um die aktuell ausgewählte Prüfung für die Detailansicht zu speichern
    val selectedExam = mutableStateOf<Exam?>(null)
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addExam(context: Context) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            errorMessage.value = context.getString(R.string.error_not_logged_in)
            return
        }

        if (subject.value.isBlank() || date.value.isBlank()) {
            errorMessage.value = context.getString(R.string.error_fill_required_fields)
            return
        }

        isLoading.value = true
        errorMessage.value = null

        val exam = hashMapOf(
            "subject" to subject.value,
            "date" to date.value,
            "room" to room.value,
            "notes" to notes.value,
            "userId" to currentUser.uid
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
                errorMessage.value = context.getString(R.string.error_exam_not_saved)
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

    fun loadExams(context: Context) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            exams.value = emptyList()
            return
        }

        isLoading.value = true
        errorMessage.value = null

        db.collection("exams")
            .whereEqualTo("userId", currentUser.uid)
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
                errorMessage.value = context.getString(R.string.error_load_exam)
            }
    }

    fun deleteExam(examId: String, context: Context) {
        val currentUser = auth.currentUser ?: return

        db.collection("exams").document(examId).get().addOnSuccessListener { document ->
            if (document.getString("userId") == currentUser.uid) {
                db.collection("exams")
                    .document(examId)
                    .delete()
                    .addOnSuccessListener {
                        loadExams(context)
                    }
                    .addOnFailureListener {
                        errorMessage.value = context.getString(R.string.error_delete_exam)
                    }
            }
        }
    }


    // KI-Unterstützung: loadExamById() wurde mit KI-Hilfe erstellt –
    // lädt eine einzelne Prüfung anhand ihrer Firestore-ID für die Detailansicht
    fun loadExamById(id: String, context: Context) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            errorMessage.value = context.getString(R.string.error_not_logged_in)
            return
        }

        isLoading.value = true
        errorMessage.value = null

        db.collection("exams").document(id)
            .get()
            .addOnSuccessListener { document ->
                isLoading.value = false
                if (document.exists() && document.getString("userId") == currentUser.uid) {
                    selectedExam.value = Exam(
                        id = document.id,
                        subject = document.getString("subject") ?: "",
                        date = document.getString("date") ?: "",
                        room = document.getString("room") ?: "",
                        notes = document.getString("notes") ?: ""
                    )
                } else {
                    errorMessage.value = context.getString(R.string.error_exam_not_found_or_access_denied)
                }
            }
            .addOnFailureListener {
                isLoading.value = false
                errorMessage.value = context.getString(R.string.error_load_exam)
            }
    }
}

