package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.res.stringResource
import com.kadyrova.count2exam.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.viewmodel.RegisterViewModel
import com.kadyrova.count2exam.ui.components.AppHeader

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    val context = LocalContext.current

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
        onRegisterClick = { viewModel.register(context) },
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
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        AppHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(34.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = firstName,
                onValueChange = onFirstNameChange,
                label = { Text(stringResource(R.string.first_name_label)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lastName,
                onValueChange = onLastNameChange,
                label = { Text(stringResource(R.string.last_name_label)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = onUsernameChange,
                label = { Text(stringResource(R.string.username_label)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))

            EmailTextField(
                value = email,
                onValueChange = onEmailChange,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            PasswordTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.password_label),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            PasswordTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = stringResource(R.string.password_repeat_label),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onRegisterClick()
                    }
                )
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
                Text(if (isLoading) stringResource(R.string.loading) else stringResource(R.string.register_button))
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onBackClick
            ) {
                Text(stringResource(R.string.back_to_login))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenContent()
}
