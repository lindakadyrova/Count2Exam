package com.kadyrova.count2exam

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kadyrova.count2exam.ui.screens.ActiveSessionScreen
import com.kadyrova.count2exam.ui.screens.AddExamScreen
import com.kadyrova.count2exam.ui.screens.CalendarScreen
import com.kadyrova.count2exam.ui.screens.EditExamScreen
import com.kadyrova.count2exam.ui.screens.ExamDetailScreen
import com.kadyrova.count2exam.ui.screens.ExamListScreen
import com.kadyrova.count2exam.ui.screens.LoginScreen
import com.kadyrova.count2exam.ui.screens.PWForgottenScreen
import com.kadyrova.count2exam.ui.screens.RegisterScreen
import com.kadyrova.count2exam.ui.theme.Count2ExamTheme
import com.kadyrova.count2exam.utils.NotificationHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
        enableEdgeToEdge()
        NotificationHelper.createNotificationChannel(this)
        //NotificationHelper.scheduleTestAlarm(this)
        setContent {
            Count2ExamTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("dashboard") },
                onRegisterClick = { navController.navigate("register") },
                onForgotPasswordClick = { navController.navigate("pwforgotten") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("login") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("pwforgotten") {
            PWForgottenScreen(
                onResetSuccess = { navController.navigate("login") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("dashboard") {
            HomeScreen(
                onAddExamClick = {
                    navController.navigate("addExam")
                },
                onEditExamClick = {
                    navController.navigate("examList")
                },
                onCalendarClick = {
                    navController.navigate("calendar")
                },
                onExamDetailClick = { examId ->
                    navController.navigate("examDetail/$examId")
                },
                onStartSessionClick = { examId, examSubject ->
                    navController.navigate("activeSession/$examId/$examSubject")
                }
            )
        }
        composable("editExam/{examId}") { backStackEntry ->
            val examId = backStackEntry.arguments?.getString("examId") ?: ""

            EditExamScreen(
                examId = examId,
                onSaveSuccess = {
                    navController.navigate("examList")
                },
                onDiscard = {
                    navController.navigate("examList")
                }
            )
        }
        composable("examDetail/{examId}") { backStackEntry ->
            val examId = backStackEntry.arguments?.getString("examId") ?: ""

            ExamDetailScreen(
                examId = examId,
                onStartSessionClick = { id, subject ->
                    navController.navigate("activeSession/$id/$subject")
                },
                onEditClick = { id ->
                    navController.navigate("editExam/$id")
                },
                onDeleteSuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            "activeSession/{examId}/{examSubject}",
            arguments = listOf(
                navArgument("examId") { type = NavType.StringType },
                navArgument("examSubject") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val examId = backStackEntry.arguments?.getString("examId") ?: ""
            val examSubject = backStackEntry.arguments?.getString("examSubject") ?: ""
            ActiveSessionScreen(
                examId = examId,
                examSubject = examSubject,
                onSessionSaved = { navController.popBackStack() }
            )
        }
        composable("calendar") {
            CalendarScreen(
                onExamClick = { examId ->
                    navController.navigate("examDetail/$examId")
                }
            )
        }
        composable("addExam") {
            AddExamScreen()
        }
        composable("examList") {
            ExamListScreen(
                onExamClick = { examId ->
                    navController.navigate("editExam/$examId")
                }
            )
        }
    }

}

