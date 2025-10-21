package com.example.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TextScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(28.dp)
                .clickable { navController.popBackStack() }
        )

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = Color.Black)) {
                    append("The ")
                }
                withStyle(SpanStyle(color = Color.Black, textDecoration = TextDecoration.LineThrough)) {
                    append("quick ")
                }
                withStyle(SpanStyle(color = Color(0xFFA0522D), fontWeight = FontWeight.Bold)) {
                    append("Brown\n")
                }
                withStyle(SpanStyle(color = Color.Black)) {
                    append("fox j u m p s ")
                }
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
                    append("over\n")
                }
                withStyle(SpanStyle(color = Color.Black)) {
                    append("the ")
                }
                withStyle(SpanStyle(fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline)) {
                    append("lazy ")
                }
                withStyle(SpanStyle(color = Color.Black)) {
                    append("dog.")
                }
            },
            fontSize = 22.sp,
            lineHeight = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            onClick = { navController.navigate("welcome") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Back", fontSize = 18.sp)
        }
    }
}
