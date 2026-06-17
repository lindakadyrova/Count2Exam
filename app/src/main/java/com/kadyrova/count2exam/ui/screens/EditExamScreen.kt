package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.EditExamViewModel

@Composable
fun EditExamScreen(
    viewModel: EditExamViewModel = viewModel()
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
                text = "Prüfung bearbeiten",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Fach *",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.subject.value,
                onValueChange = { viewModel.subject.value = it },
                placeholder = { Text("Robotics") },
                trailingIcon = {
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Datum *",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.date.value,
                onValueChange = { viewModel.date.value = it },
                placeholder = { Text("11.02.2026") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Datum auswählen"
                    )
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Notizen",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

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

            Spacer(modifier = Modifier.height(32.dp))

            viewModel.errorMessage.value?.let { error ->
                Text(text = error, color = Color.Red, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(12.dp))
            }


            Button(
                onClick = { viewModel.save()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = "Speichern",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.discard() },
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary
                ),
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
fun EditExamScreenPreview() {
    EditExamScreen()
}