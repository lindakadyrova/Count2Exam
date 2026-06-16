package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.ExamViewModel

@Composable
fun AddExamScreen(
    viewModel: ExamViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(34.dp)
        ) {
            Text(
                text = "Prüfung hinzufügen",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text("Fach *", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.subject.value,
                onValueChange = { viewModel.subject.value = it },
                placeholder = { Text("zb. Robotics") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text("Datum *", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.date.value,
                onValueChange = { viewModel.date.value = it },
                placeholder = { Text("11.02.2026") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Datum auswählen")
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text("Raum", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.room.value,
                onValueChange = { viewModel.room.value = it },
                placeholder = { Text("zb. 102b30i") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text("Notizen", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.notes.value,
                onValueChange = { viewModel.notes.value = it },
                placeholder = { Text("Die Prüfung...") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            viewModel.errorMessage.value?.let {
                Text(text = it, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (viewModel.addSuccess.value) {
                Text(
                    text = "Prüfung wurde gespeichert!",
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = { viewModel.addExam() },
                enabled = !viewModel.isLoading.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = if (viewModel.isLoading.value) "Speichern..." else "Prüfung hinzufügen"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    viewModel.clearFields()
                },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Verwerfen")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddExamScreenPreview() {
    AddExamScreen()
}