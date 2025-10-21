package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.ForgotPasswordViewModel

@Composable
fun ResetPasswordScreen(navController: NavHostController, viewModel: ForgotPasswordViewModel) {
    var password by remember { mutableStateOf(viewModel.password) }
    var confirm by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create new password", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            label = { Text("Confirm Password") }
        )
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (password == confirm && password.isNotEmpty()) {
                viewModel.password = password
                navController.navigate("confirm")
            }
        }) {
            Text("Next")
        }
    }
}
