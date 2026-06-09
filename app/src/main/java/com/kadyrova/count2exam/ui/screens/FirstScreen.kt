package com.kadyrova.count2exam.ui.screens

import android.R.attr.password
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun FirstScreen() {
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
            label = { Text("Benutzername") }
        )
        Spacer(modifier = Modifier.height(16.dp))
//
        PasswordTextField("Passwort")

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { }) {
            Text("Login")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { }) {

            Text("Register")
        }
    }
}

@Composable
fun PasswordTextField(label: String) {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(label) },
        visualTransformation =
            if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )}

// Für die Passwort-Anzeige wurde die offizielle Android-Dokumentation als Grundlage verwendet:
// https://developer.android.com/develop/ui/compose/quick-guides/content/show-hide-password?hl=de
// Da das Beispiel dort mit einem anderen Textfeld umgesetzt wurde, habe ich zusätzlich KI-Unterstützung genutzt,
// um die Lösung auf ein OutlinedTextField anzupassen.


@Preview(showSystemUi = true)
@Composable
fun FirstScreenPreview() {
    FirstScreen()
}