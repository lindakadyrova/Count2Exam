package com.kadyrova.count2exam

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kadyrova.count2exam.ui.screens.AddExamScreen
import com.kadyrova.count2exam.ui.screens.CalendarScreen
import com.kadyrova.count2exam.ui.screens.EditExamScreen
import com.kadyrova.count2exam.ui.screens.ExamDetailScreen
import com.kadyrova.count2exam.ui.screens.ExamListScreen
import com.kadyrova.count2exam.ui.screens.LoginScreen
import com.kadyrova.count2exam.ui.screens.PWForgottenScreen
import com.kadyrova.count2exam.ui.screens.RegisterScreen
import com.kadyrova.count2exam.ui.theme.Count2ExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

            ExamDetailScreen(examId = examId)
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

