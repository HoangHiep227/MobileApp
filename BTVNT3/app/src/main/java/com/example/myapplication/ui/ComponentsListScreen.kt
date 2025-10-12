package com.example.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun ComponentsListScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "UI Components List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CategoryHeader("Display")
        ComponentCard("Text", "Displays text") { navController.navigate("textScreen") }
        ComponentCard("Image", "Displays an image") { navController.navigate("imageScreen") }

        CategoryHeader("Input")
        ComponentCard("TextField", "Input field for text") { navController.navigate("textFieldScreen") }
        ComponentCard("PasswordField", "Input field for passwords") { navController.navigate("passwordFieldScreen") }
        ComponentCard("Checkbox", "Toggles a binary choice") { navController.navigate("checkboxScreen") }
        ComponentCard("Switch", "Turns an option on or off") { navController.navigate("switchScreen") }

        CategoryHeader("Layout")
        ComponentCard("Column", "Arranges elements vertically") { navController.navigate("columnScreen") }
        ComponentCard("Row", "Arranges elements horizontally") { navController.navigate("rowScreen") }
        ComponentCard("Box", "Overlaps elements") { navController.navigate("boxScreen") }

        CategoryHeader("Buttons & Interaction")
        ComponentCard("Button", "Standard clickable button") { navController.navigate("buttonScreen") }
        ComponentCard("IconButton", "Clickable icon button") { navController.navigate("iconButtonScreen") }
        ComponentCard("FloatingActionButton", "Floating circular button") { navController.navigate("fabScreen") }

        CategoryHeader("Containers")
        ComponentCard("Card", "Displays content with elevation") { navController.navigate("cardScreen") }
        ComponentCard("Surface", "Applies color and elevation") { navController.navigate("surfaceScreen") }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CategoryHeader(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun ComponentCard(title: String, description: String, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}
