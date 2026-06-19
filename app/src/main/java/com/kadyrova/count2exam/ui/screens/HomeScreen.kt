import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.kadyrova.count2exam.ui.components.AppHeader
import com.kadyrova.count2exam.R
import com.kadyrova.count2exam.viewmodel.ExamViewModel

@Composable
fun HomeScreen(
    onAddExamClick: () -> Unit,
    onEditExamClick: () -> Unit,
    examViewModel: ExamViewModel = viewModel(),
    onCalendarClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        examViewModel.loadExams()
    }

    var isMenuOpen by remember { mutableStateOf(false) }
    val examCount = examViewModel.exams.value.size
    val nextExam = examViewModel.exams.value.minByOrNull { it.date }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppHeader()

                IconButton(
                    onClick = { isMenuOpen = true },
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.menu_open)
                    )
                }
            }
            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "${examViewModel.exams.value.size} ${stringResource(R.string.exams_open)}",
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "${stringResource(R.string.next_exam)} 7t 8h 25min",
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(90.dp))

            Button(
                onClick = onAddExamClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_exam)
                )
            }
            Spacer(modifier = Modifier.height(17.dp))

            Button(
                onClick = onEditExamClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text(
                    text = stringResource(R.string.edit_exam)
                )
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
        onAddExamClick = {},
        onEditExamClick = {},
        onCalendarClick = {}
    )
}