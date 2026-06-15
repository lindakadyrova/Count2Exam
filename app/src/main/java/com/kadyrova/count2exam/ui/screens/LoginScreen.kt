package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.R
import com.kadyrova.count2exam.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    LaunchedEffect(viewModel.loginSuccess.value) {
        if (viewModel.loginSuccess.value) {
            onLoginSuccess()
        }
    }

    LoginScreenContent(
        email = viewModel.email.value,
        password = viewModel.password.value,
        isLoading = viewModel.isLoading.value,
        errorMessage = viewModel.errorMessage.value,
        onEmailChange = { viewModel.email.value = it },
        onPasswordChange = { viewModel.password.value = it },
        onLoginClick = { viewModel.login() },
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick
    )
}

@Composable
fun LoginScreenContent(
    email: String = "",
    password: String = "",
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(34.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Logo(
            modifier = Modifier.height(250.dp)
        )

        EmailTextField(
            value = email,
            onValueChange = onEmailChange
        )

        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            "Passwort",
            value = password,
            onValueChange = onPasswordChange
        )

        if (errorMessage != null) {
            Text(text = errorMessage, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Passwort vergessen?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = onLoginClick,
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Laden..." else "Login")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = onRegisterClick,
        ) {
            Text("Registrieren")
        }
    }
}

@Composable
fun PasswordTextField(
    label: String,
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
    )
}

@Composable
fun Logo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.count2exam),
        contentDescription = "Count2Exam Logo",
        modifier = modifier
    )
}

// Für die Passwort-Anzeige wurde die offizielle Android-Dokumentation als Grundlage verwendet:
// https://developer.android.com/develop/ui/compose/quick-guides/content/show-hide-password?hl=de
// Da das Beispiel dort mit einem anderen Textfeld umgesetzt wurde, habe ich zusätzlich KI-Unterstützung genutzt,
// um die Lösung auf ein OutlinedTextField anzupassen.


@Preview(showSystemUi = true)
@Composable
fun FirstScreenPreview() {
    LoginScreenContent()
}