package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.ExamViewModel

@Composable
fun ExamDetailScreen(
    examId: String,
    onStartSessionClick: (String, String) -> Unit = { _, _ -> },
    viewModel: ExamViewModel = viewModel()
) {
    val exam = viewModel.selectedExam.value

    LaunchedEffect(examId) {
        viewModel.loadExamById(examId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        AppHeader()

        if (exam == null) {
            Text(text = "Lädt...")
        } else {
            Text(
                text = exam.subject,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Datum: ${exam.date}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Raum: ${exam.room}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Notizen: ${exam.notes}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { onStartSessionClick(exam.id, exam.subject) }) {
                Text("Lernsession starten")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ExamDetailScreenPreview() {
    ExamDetailScreen(examId = "test-id")
}