package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.viewmodel.RegisterViewModel
import com.kadyrova.count2exam.ui.components.AppHeader

// AI-assisted: split into two composables so the preview works without a ViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    LaunchedEffect(viewModel.registerSuccess.value) {
        if (viewModel.registerSuccess.value) {
            onRegisterSuccess()
        }
    }

    RegisterScreenContent(
        firstName = viewModel.firstName.value,
        lastName = viewModel.lastName.value,
        username = viewModel.username.value,
        email = viewModel.email.value,
        password = viewModel.password.value,
        confirmPassword = viewModel.confirmPassword.value,
        isLoading = viewModel.isLoading.value,
        errorMessage = viewModel.errorMessage.value,
        onFirstNameChange = { viewModel.firstName.value = it },
        onLastNameChange = { viewModel.lastName.value = it },
        onUsernameChange = { viewModel.username.value = it },
        onEmailChange = { viewModel.email.value = it },
        onPasswordChange = { viewModel.password.value = it },
        onConfirmPasswordChange = { viewModel.confirmPassword.value = it },
        onRegisterClick = { viewModel.register() },
        onBackClick = onBackClick
    )
}

@Composable
fun RegisterScreenContent(
    firstName: String = "",
    lastName: String = "",
    username: String = "",
    email: String = "",
    password: String = "",
    confirmPassword: String = "",
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onFirstNameChange: (String) -> Unit = {},
    onLastNameChange: (String) -> Unit = {},
    onUsernameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    AppHeader()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(34.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text("Vorname") },
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text("Nachname") },
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Benutzername") },
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = { Text("E-Mail") }
        )
        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Passwort"
        )
        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Passwort wiederholen"
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRegisterClick,
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Laden..." else "Registrieren")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onBackClick
        ) {
            Text("Zurück zum Login")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenContent()
}