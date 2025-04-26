package com.example.monee.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.monee.ui.model.Transaction
import com.example.monee.ui.screen.AddTransactionScreen
import com.example.monee.ui.screen.HomeScreen
import com.example.monee.ui.screen.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val ADD_TRANSACTION = "add_transaction"
}

@Composable
fun NavGraph(navController: NavHostController) {
    var transactions by rememberSaveable {
        mutableStateOf(
            listOf(
                Transaction("Lunch", -25000, "Food"),
                Transaction("Salary", 5000000, "Salary"),
                Transaction("Coffee", -15000, "Food")
            )
        )
    }
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                transactions = transactions,
                onAddTransactionClick = {
                    navController.navigate(Routes.ADD_TRANSACTION)
                }
            )
        }
        composable(Routes.ADD_TRANSACTION) {
            AddTransactionScreen(
                navController = navController,
                onTransactionAdded = { transaction ->
                    transactions = transactions + transaction
                }
            )
        }
    }
}