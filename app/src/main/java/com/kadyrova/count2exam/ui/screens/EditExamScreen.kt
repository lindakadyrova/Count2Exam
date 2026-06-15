package com.kadyrova.count2exam.ui.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


@Composable
fun EditExamScreen(){

    var subject by remember { mutableStateOf("Robotics") }
    var date by remember { mutableStateOf("11.02.2026") }
    var notes by remember {mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "C2E",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "Prüfung bearbeiten",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Fach",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = subject,
            onValueChange = {subject = it},
            placeholder = {Text("Robotics")},
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Bearbeiten"

                )
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Datum",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = date,
            onValueChange = {date = it },
            placeholder = {Text("11.02.2026")},
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
            value = notes,
            onValueChange = {notes = it},
            placeholder = {Text("Die Prüfung... ")},
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50.dp),
        ){
            Text(
                text = "Speichern",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50.dp),
        ){
            Text(
                text = "Verwerfen",
                color = Color.White
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun EditExamScreenPreview(){
    EditExamScreen()
}