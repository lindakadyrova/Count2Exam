package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.ExamViewModel
import com.kadyrova.count2exam.viewmodel.StudySessionViewModel

@Composable
fun ExamDetailScreen(
    examId: String,
    onStartSessionClick: (String, String) -> Unit = { _, _ -> },
    onEditClick: (String) -> Unit = {},
    onDeleteSuccess: () -> Unit = {},
    viewModel: ExamViewModel = viewModel(),
    sessionViewModel: StudySessionViewModel = viewModel()
) {
    val exam = viewModel.selectedExam.value

    LaunchedEffect(examId) {
        viewModel.loadExamById(examId)
        sessionViewModel.loadStatsForExam(examId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        AppHeader()

        Spacer(modifier = Modifier.height(40.dp))

        if (exam == null) {
            Text(text = "Lädt...")
        } else {
            Text(
                text = exam.subject,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    DetailRow(label = "Datum", value = exam.date)
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailRow(label = "Raum", value = exam.room.ifBlank { "—" })
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailRow(label = "Notizen", value = exam.notes.ifBlank { "—" })
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Lernstatistik",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow(
                        label = "Gesamtzeit",
                        value = formatTime(sessionViewModel.totalSeconds.value)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow(
                        label = "Sessions",
                        value = sessionViewModel.sessionCount.value.toString()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { onEditClick(exam.id) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Bearbeiten")
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.deleteExam(exam.id)
                        onDeleteSuccess()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Löschen")
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 17.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ExamDetailScreenPreview() {
    ExamDetailScreen(examId = "test-id")
}