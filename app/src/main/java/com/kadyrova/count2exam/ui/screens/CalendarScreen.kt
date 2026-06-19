package com.kadyrova.count2exam.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.viewmodel.Exam
import com.kadyrova.count2exam.viewmodel.ExamViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun CalendarWdiget(
    exams: List<Exam>,
    onDayClick: (Exam) -> Unit
) {

    val today = LocalDate.now()
    val monthName = today.month.getDisplayName(TextStyle.FULL, Locale.GERMAN)
    val daysInMonth = today.lengthOfMonth()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(280.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = monthName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFD78DA7)
            )
            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD27798))
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("M", "T", "W", "T", "F", "S", "S").forEach { day ->
                    Text(
                        text = day,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))


            val heighlightedDay = 22

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.height(180.dp)
            ) {
                items(daysInMonth) { index ->
                    val day = index + 1
                    val dayDate = today.withDayOfMonth(day)
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    val formattedDate = dayDate.format(formatter)
                    val examOnThisDay = exams.find { it.date == dayDate.toString() }

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(
                                if (examOnThisDay != null) Color(0xFFF8BBD0) else Color.Transparent
                            )
                            .then(
                                if (examOnThisDay != null)
                                    Modifier.clickable { onDayClick(examOnThisDay) }
                                else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.toString(),
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun CalendarScreen(
    examViewModel: ExamViewModel = viewModel(),
    onExamClick: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        examViewModel.loadExams()
    }

    CalendarWdiget(
        exams = examViewModel.exams.value,
        onDayClick = { exam -> onExamClick(exam.id) }
    )
}

@Preview(showSystemUi = true)
@Composable
fun CalendarWdigetPreview() {
    CalendarWdiget(
        exams = emptyList(),
        onDayClick = {}
    )
}