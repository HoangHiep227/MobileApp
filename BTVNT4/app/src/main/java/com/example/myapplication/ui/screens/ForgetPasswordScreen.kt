package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ForgotPasswordViewModel
import android.util.Patterns

@Composable
fun ForgetPasswordScreen(navController: NavHostController, viewModel: ForgotPasswordViewModel) {
    var email by remember { mutableStateOf(viewModel.email) }
    var emailError by remember { mutableStateOf("") }
    val showSummary = viewModel.shouldShowSummaryDialog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "UTH Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )
        
        Text(
            text = "SmartTasks",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.ui.graphics.Color(0xFF1976D2),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Text(
            text = "Forget Password?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Enter your Email, we will send you a verification code.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = androidx.compose.ui.graphics.Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { 
                email = it
                emailError = ""
            },
            label = { Text("Your Email") },
            isError = emailError.isNotEmpty(),
            supportingText = if (emailError.isNotEmpty()) { { Text(emailError) } } else null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = androidx.compose.ui.graphics.Color(0xFF1976D2),
                focusedLabelColor = androidx.compose.ui.graphics.Color(0xFF1976D2),
                unfocusedBorderColor = androidx.compose.ui.graphics.Color(0xFFE0E0E0),
                unfocusedContainerColor = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
            )
        )

        Button(
            onClick = {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    viewModel.email = email
                    navController.navigate("verify")
                } else {
                    emailError = "Email không hợp lệ"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color(0xFF1976D2)
            )
        ) {
            Text("Next", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.White)
        }

        if (showSummary) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.resetAll()
                },
                confirmButton = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(onClick = { viewModel.resetAll() }) { 
                            Text("Đóng") 
                        }
                        Button(
                            onClick = { 
                                viewModel.resetAll()
                                navController.navigate("library")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = androidx.compose.ui.graphics.Color(0xFF1976D2) // Blue 700
                            )
                        ) { 
                            Text("Đăng nhập", color = androidx.compose.ui.graphics.Color.White) 
                        }
                    }
                },
                title = { Text("Thông tin đã nhập") },
                text = {
                    Column {
                        Text("Email: ${viewModel.email}")
                        Text("Mã xác thực: ${viewModel.code}")
                        Text("Mật khẩu: ${viewModel.password}")
                    }
                }
            )
        }
    }
}
