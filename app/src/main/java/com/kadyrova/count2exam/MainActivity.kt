package com.kadyrova.count2exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            // placeholder so app won't crash
        }
    }
}
