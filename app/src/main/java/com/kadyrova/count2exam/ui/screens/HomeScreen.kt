import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.R
import com.kadyrova.count2exam.ui.components.ExamCard
import com.kadyrova.count2exam.viewmodel.ExamViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    navController: NavController,
    onAddExamClick: () -> Unit,
//    onEditExamClick: () -> Unit,
    examViewModel: ExamViewModel = viewModel(),
    onCalendarClick: () -> Unit,
    onExamDetailClick: (String) -> Unit = {},
    onStartSessionClick: (String, String) -> Unit = { _, _ -> }
) {
    LaunchedEffect(Unit) {
        examViewModel.loadExams()
    }

    var isMenuOpen by remember { mutableStateOf(false) }
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val exams = examViewModel.exams.value
        .sortedBy { exam ->
            try {
                LocalDate.parse(exam.date, formatter)
            } catch (_: Exception) {
                LocalDate.MAX
            }
        }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppHeader()

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onCalendarClick() }) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = stringResource(R.string.show_calendar)
                        )
                    }

                    IconButton(onClick = {
                        com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                        navController.navigate("login") { popUpTo(0) }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            }


            if (exams.isEmpty()) {
                Text("Keine Prüfungen vorhanden")
            } else {
                val pagerState = rememberPagerState(pageCount = { exams.size })

                HorizontalPager(
                    state = pagerState,
                    pageSize = PageSize.Fixed(280.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 32.dp),
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    val exam = exams[page]
                    val examDate = try {
                        LocalDate.parse(exam.date, formatter)
                    } catch (_: Exception) {
                        null
                    }
                    val daysUntilExam = examDate?.toEpochDay()?.minus(LocalDate.now().toEpochDay())

                    ExamCard(
                        exam = exam,
                        daysUntilExam = daysUntilExam,
                        onDetailsClick = { onExamDetailClick(exam.id) },
                        onStartSessionClick = { onStartSessionClick(exam.id, exam.subject) },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            Button(
                onClick = onAddExamClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(stringResource(R.string.add_exam))
            }
        }

        if (isMenuOpen) {
            SideMenu(
                onClose = { isMenuOpen = false },
                onCalendarClick = onCalendarClick,
                onSettingsClick = {}
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = androidx.navigation.compose.rememberNavController(), //Mit hilfe von KI gelöst
        onAddExamClick = {},
//        onEditExamClick = {},
        onCalendarClick = {},
    )
}