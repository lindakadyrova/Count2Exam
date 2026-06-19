package com.kadyrova.count2exam.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.R
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.EditExamViewModel
import java.util.Calendar
import androidx.compose.runtime.LaunchedEffect

@Composable
fun EditExamScreen(
    examId: String,
    onSaveSuccess: () -> Unit = {},
    onDiscard: () -> Unit = {},
    viewModel: EditExamViewModel = viewModel()
) {
    LaunchedEffect(examId) {
        viewModel.loadExam(examId)
    }
    val context = LocalContext.current
    LaunchedEffect(viewModel.saveSuccess.value) {
        if (viewModel.saveSuccess.value) {
            Toast.makeText(
                context,
                "Prüfung wurde gespeichert",
                Toast.LENGTH_SHORT
            ).show()

            onSaveSuccess()
        }
    }

    val calendar = Calendar.getInstance()


    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.date.value = "%02d.%02d.%04d".format(
                dayOfMonth,
                month + 1,
                year
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.minDate = System.currentTimeMillis()

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
                text = stringResource(R.string.edit_exam_title),
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(R.string.subject_label),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.subject.value,
                onValueChange = { viewModel.subject.value = it },
                placeholder = { Text(stringResource(R.string.subject_placeholder)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = stringResource(R.string.date_label),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        datePickerDialog.show()
                    }
            ) {
                OutlinedTextField(
                    value = viewModel.date.value,
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    placeholder = { Text(stringResource(R.string.date_placeholder)) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.date_picker)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = stringResource(R.string.notes_label),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.notes.value,
                onValueChange = { viewModel.notes.value = it },
                placeholder = { Text(stringResource(R.string.notes_placeholder)) },
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
                onClick = { viewModel.save(examId) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = stringResource(R.string.save),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Änderungen verworfen",
                        Toast.LENGTH_SHORT
                    ).show()

                    onDiscard()
                },
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
                Text(stringResource(R.string.discard))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EditExamScreenPreview() {
    EditExamScreen(
        examId = "preview"
    )
}