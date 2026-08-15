package com.kadyrova.count2exam.ui.screens

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kadyrova.count2exam.viewmodel.ExamViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExamScreen(
    onAddSuccess: () -> Unit = {},
    onDiscard: () -> Unit = {},
    viewModel: ExamViewModel = viewModel()
) {
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val examAddedSuccess = stringResource(R.string.exam_added_success)

    LaunchedEffect(viewModel.addSuccess.value) {
        if (viewModel.addSuccess.value) {
            Toast.makeText(
                context,
                examAddedSuccess,
                Toast.LENGTH_SHORT
            ).show()
            onAddSuccess()
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text(stringResource(R.string.enable_reminders_title)) },
            text = { Text(stringResource(R.string.enable_reminders_text)) },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                    }
                }) {
                    Text(stringResource(R.string.open_settings))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    viewModel.addExam(context)
                }) {
                    Text(stringResource(R.string.later))
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(34.dp)
        ) {
            Text(
                text = stringResource(R.string.add_exam_title),
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                stringResource(R.string.subject_label),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.subject.value,
                onValueChange = { viewModel.subject.value = it },
                placeholder = { Text(stringResource(R.string.subject_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                stringResource(R.string.date_label),
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
                        showDatePicker = true
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
                stringResource(R.string.room_label),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.room.value,
                onValueChange = { viewModel.room.value = it },
                placeholder = { Text(stringResource(R.string.room_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                stringResource(R.string.notes_label),
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

            Spacer(modifier = Modifier.height(20.dp))

            viewModel.errorMessage.value?.let {
                Text(text = it, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (viewModel.addSuccess.value) {
                Text(
                    text = stringResource(R.string.exam_saved),
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                        showPermissionDialog = true
                    } else {
                        viewModel.addExam(context)
                    }
                },
                enabled = !viewModel.isLoading.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = if (viewModel.isLoading.value)
                        stringResource(R.string.saving)
                    else
                        stringResource(R.string.add_exam_title)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val changesDiscarded = stringResource(R.string.changes_discarded)

            OutlinedButton(
                onClick = {
                    viewModel.clearFields()
                    Toast.makeText(
                        context,
                        changesDiscarded,
                        Toast.LENGTH_SHORT
                    ).show()
                    onDiscard()
                },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
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

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val cal = Calendar.getInstance().apply { timeInMillis = millis }
                            viewModel.date.value = "%02d.%02d.%04d".format(
                                cal.get(Calendar.DAY_OF_MONTH),
                                cal.get(Calendar.MONTH) + 1,
                                cal.get(Calendar.YEAR)
                            )
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK", color = Color(0xFFD78DA7))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White,
                    headlineContentColor = Color(0xFFD78DA7),
                    selectedDayContainerColor = Color(0xFFD78DA7),
                    selectedDayContentColor = Color.White,
                    todayDateBorderColor = Color(0xFFD78DA7),
                    todayContentColor = Color(0xFFD78DA7)
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddExamScreenPreview() {
    AddExamScreen()
}