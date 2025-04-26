package com.example.monee.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.monee.R
import com.example.monee.ui.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }

    val interBold = FontFamily(Font(R.font.inter_bold))

    LaunchedEffect(Unit) {
        visible = true
        delay(2000)
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

    SplashContent(fontFamily = interBold, visible = visible)
}

@Composable
fun SplashContent(
    fontFamily: FontFamily,
    visible: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8D996), // Top
                        Color(0xFF655D3C)  // Bottom
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = visible, enter = fadeIn()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "monee",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your Personal Daily Finance Tracker.",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val interBold = FontFamily.Default // fallback preview
    SplashContent(fontFamily = interBold)
}