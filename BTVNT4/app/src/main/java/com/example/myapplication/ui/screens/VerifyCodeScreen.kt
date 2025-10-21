package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ForgotPasswordViewModel

@Composable
fun VerifyCodeScreen(navController: NavHostController, viewModel: ForgotPasswordViewModel) {
    var code by remember { mutableStateOf(viewModel.code) }
    var codeError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .background(
                    color = androidx.compose.ui.graphics.Color(0xFF1976D2),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = androidx.compose.ui.graphics.Color.White
            )
        }
        
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
                text = "Verify Code",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "Enter the code we just sent you on your registered Email",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = androidx.compose.ui.graphics.Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = code,
                onValueChange = { 
                    code = it
                    codeError = ""
                },
                label = { Text("Enter the 6-digit code") },
                isError = codeError.isNotEmpty(),
                supportingText = if (codeError.isNotEmpty()) { { Text(codeError) } } else null,
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
                    if (code == "111111") {
                        viewModel.code = code
                        navController.navigate("reset")
                    } else {
                        codeError = "Mã xác thực không hợp lệ"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFF1976D2)
                )
            ) {
                Text("Next", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.White)
            }
        }
    }
}
