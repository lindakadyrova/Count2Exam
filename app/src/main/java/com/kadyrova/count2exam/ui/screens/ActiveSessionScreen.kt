package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.StudySessionViewModel
import androidx.compose.ui.res.stringResource
import com.kadyrova.count2exam.R

@Composable
fun ActiveSessionScreen(
    examId: String,
    examSubject: String,
    onSessionSaved: () -> Unit = {},
    viewModel: StudySessionViewModel = viewModel()
) {
    ActiveSessionScreenContent(
        examSubject = examSubject,
        elapsedSeconds = viewModel.elapsedSeconds.value,
        isRunning = viewModel.isRunning.value,
        onStartClick = { viewModel.startTimer() },
        onStopClick = {
            viewModel.stopAndSave(examId)
            onSessionSaved()
        }
    )
}

@Composable
fun ActiveSessionScreenContent(
    examSubject: String = "Robotics",
    elapsedSeconds: Long = 0L,
    isRunning: Boolean = false,
    onStartClick: () -> Unit = {},
    onStopClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        AppHeader()

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = examSubject,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = formatTime(elapsedSeconds),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (!isRunning) {
                Button(onClick = onStartClick) {
                    Text(stringResource(R.string.start))
                }
            } else {
                Button(onClick = onStopClick) {
                    Text(stringResource(R.string.stop))
                }
            }
        }
    }
}

fun formatTime(totalSeconds: Long): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Preview(showSystemUi = true)
@Composable
fun ActiveSessionScreenPreview() {
    ActiveSessionScreenContent()
}