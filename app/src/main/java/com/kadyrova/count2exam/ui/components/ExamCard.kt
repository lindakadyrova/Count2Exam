package com.kadyrova.count2exam.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadyrova.count2exam.viewmodel.Exam
import com.kadyrova.count2exam.R

@Composable
fun ExamCard(
    exam: Exam,
    daysUntilExam: Long?,
    onDetailsClick: () -> Unit,
    onStartSessionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = exam.subject,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (daysUntilExam) {
                    null -> ""
                    0L -> stringResource(R.string.today)
                    1L -> stringResource(R.string.one_day_left)
                    else -> stringResource(R.string.days_left, daysUntilExam)
                },
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onStartSessionClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.start_session))
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDetailsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.show_details))
            }
        }
    }
}