package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(34.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            state = rememberTextFieldState(),
            label = { Text("Vorname") },
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            state = rememberTextFieldState(),
            label = { Text("Nachname") },
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            state = rememberTextFieldState(),
            label = { Text("Benutzername") },
        )

        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField("Passwort")

        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField("Passwort wiederholen")

        Spacer(modifier = Modifier.height(20.dp))

        Button(  modifier = Modifier.fillMaxWidth(),
            onClick = { }) {
            Text("Registrieren")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}