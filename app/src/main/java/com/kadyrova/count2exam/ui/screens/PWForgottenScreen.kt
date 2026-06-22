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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.PWForgottenViewModel
import com.kadyrova.count2exam.R
import androidx.compose.ui.platform.LocalContext


@Composable
fun PWForgottenScreen(
    onResetSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: PWForgottenViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.resetSuccess.value) {
        if (viewModel.resetSuccess.value) {
            onResetSuccess()
        }
    }

    PWForgottenScreenContent(
        email = viewModel.email.value,
        isLoading = viewModel.isLoading.value,
        errorMessage = viewModel.errorMessage.value,
        resetSuccess = viewModel.resetSuccess.value,
        onEmailChange = { viewModel.email.value = it },
        onResetClick = { viewModel.resetPassword(context) },
        onBackClick = onBackClick
    )
}

@Composable
fun PWForgottenScreenContent(
    email: String = "",
    isLoading: Boolean = false,
    errorMessage: String? = null,
    resetSuccess: Boolean = false,
    onEmailChange: (String) -> Unit = {},
    onResetClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(34.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(
                value = email,
                onValueChange = onEmailChange
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (errorMessage != null) {
                Text(text = errorMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (resetSuccess) {
                Text(text = stringResource(R.string.reset_email_sent), color = Color.Green)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = onResetClick,
                enabled = !isLoading
            ) {
                Text(if (isLoading) stringResource(R.string.loading) else stringResource(R.string.reset_password_button))
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                onClick = onBackClick
            ) {
                Text(stringResource(R.string.back_to_login))
            }
        }
    }
}

@Composable
fun EmailTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.email_label)) },
        modifier = Modifier.fillMaxWidth()
    )

}


@Preview(showSystemUi = true)
@Composable
fun PWForgottenScreenPreview() {
    PWForgottenScreenContent()
}