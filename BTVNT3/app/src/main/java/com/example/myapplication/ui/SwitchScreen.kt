package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SwitchScreen(navController: NavController) {
    var switchOn by remember { mutableStateOf(false) }

    val backgroundColor = if (switchOn) Color.Black else Color(0xFFF5F5F5)
    val textColor = if (switchOn) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = textColor,
            modifier = Modifier
                .size(28.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(100.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = switchOn,
                    onCheckedChange = { switchOn = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (switchOn) "Dark Mode ON" else "Light Mode ON",
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (switchOn) "🌙 Dark Mode Activated" else "☀️ Light Mode Activated",
                color = textColor
            )
        }
    }
}
