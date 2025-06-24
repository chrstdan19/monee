package com.example.monee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.monee.ui.navigation.NavGraph
import com.example.monee.ui.theme.MoneeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneeTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
