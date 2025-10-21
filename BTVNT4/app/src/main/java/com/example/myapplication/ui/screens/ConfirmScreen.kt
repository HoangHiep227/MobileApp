package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun ConfirmScreen(navController: NavHostController, viewModel: ForgotPasswordViewModel) {
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
                text = "Confirm",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "We are here to help you!",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = androidx.compose.ui.graphics.Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Email, contentDescription = "Email", tint = androidx.compose.ui.graphics.Color(0xFF1976D2))
                    Spacer(Modifier.width(12.dp))
                    Text(viewModel.email, fontSize = 16.sp)
                }
            }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Phone, contentDescription = "Code", tint = androidx.compose.ui.graphics.Color(0xFF1976D2))
                    Spacer(Modifier.width(12.dp))
                    Text(viewModel.code, fontSize = 16.sp)
                }
            }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Lock, contentDescription = "Password", tint = androidx.compose.ui.graphics.Color(0xFF1976D2))
                    Spacer(Modifier.width(12.dp))
                    Text("********", fontSize = 16.sp)
                }
            }

            Button(
                onClick = {
                    viewModel.shouldShowSummaryDialog = true
                    navController.navigate("forget") {
                        popUpTo("forget") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFF1976D2)
                )
            ) {
                Text("Submit", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.White)
            }
        }
    }
}
