package com.kadyrova.count2exam.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadyrova.count2exam.ui.components.AppHeader
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height

@Composable
fun ExamListScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppHeader()

        Text(
            text = "Meine Prüfungen",
            fontSize = 20.sp,
            modifier = Modifier.padding(34.dp)
        )
        ExamCard("Robotics", "11.02.2026")
        ExamCard("IT-Recht", "20.02.2026")
        ExamCard("Kotlin", "01.03.2026")
    }
}

@Composable
fun ExamCard(subject: String, date: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 34.dp, vertical = 8.dp)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = subject,
                    fontSize = 16.sp
                )

                Text(
                    text = date,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { }) {
                Icon(Icons.Default.Edit, contentDescription = "Bearbeiten")
            }

            IconButton(onClick = { }) {
                Icon(Icons.Default.Delete, contentDescription = "Löschen")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ExamListScreenPreview() {
    ExamListScreen()
}