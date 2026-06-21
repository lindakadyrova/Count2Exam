package com.kadyrova.count2exam.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StudySessionViewModel : ViewModel() {
    val elapsedSeconds = mutableStateOf(0L)
    val isRunning = mutableStateOf(false)
    val sessionSaved = mutableStateOf(false)
    val totalSeconds = mutableStateOf(0L)
    val sessionCount = mutableStateOf(0)

    private var timerJob: Job? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun startTimer() {
        isRunning.value = true
        timerJob = viewModelScope.launch {
            while (isRunning.value) {
                delay(1000)
                elapsedSeconds.value += 1
            }
        }
    }

    fun stopAndSave(examId: String) {
        isRunning.value = false
        timerJob?.cancel()

        val uid = auth.currentUser?.uid ?: return
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY)
        val today = dateFormat.format(Date())

        val session = hashMapOf(
            "examId" to examId,
            "durationSeconds" to elapsedSeconds.value,
            "date" to today
        )

        db.collection("users").document(uid)
            .collection("sessions").add(session)
            .addOnSuccessListener {
                sessionSaved.value = true
                elapsedSeconds.value = 0
            }
    }

    fun loadStatsForExam(examId: String) {
        val uid = auth.currentUser?.uid ?: return

        db.collection("users").document(uid)
            .collection("sessions")
            .whereEqualTo("examId", examId)
            .get()
            .addOnSuccessListener { result ->
                val sessions = result.documents
                sessionCount.value = sessions.size
                totalSeconds.value = sessions.sumOf { it.getLong("durationSeconds") ?: 0L }
            }
    }
}