package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.*
import com.example.myapplication.viewmodel.ForgotPasswordViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    val viewModel: ForgotPasswordViewModel = viewModel()
    NavHost(navController = navController, startDestination = "forget") {
        composable("forget") { ForgetPasswordScreen(navController, viewModel) }
        composable("verify") { VerifyCodeScreen(navController, viewModel) }
        composable("reset") { ResetPasswordScreen(navController, viewModel) }
        composable("confirm") { ConfirmScreen(navController, viewModel) }
        composable("library") { LibraryScreen(navController) }
    }
}
