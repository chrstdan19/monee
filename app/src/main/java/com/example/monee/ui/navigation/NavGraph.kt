package com.example.monee.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.monee.data.fake.UserStorage
import com.example.monee.model.Transaction
import com.example.monee.model.TransactionType

import com.example.monee.ui.screen.SplashScreen
import com.example.monee.ui.screen.HomeScreen


import com.example.monee.ui.screen.LoginScreen
import com.example.monee.ui.screen.RegisterScreen

import com.example.monee.ui.screen.AddTransactionScreen


object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val ADD_TRANSACTION = "add_transaction"
}

@Composable
fun NavGraph(navController: NavHostController) {
    var transactions by rememberSaveable {
        mutableStateOf(
            listOf(
                Transaction(
                    id = "1",
                    type = TransactionType.EXPENSE,
                    category = "Food",
                    amount = -25000L,
                    date = "2025-04-26",
                    note = "Lunch"
                ),
                Transaction(
                    id = "2",
                    type = TransactionType.INCOME,
                    category = "Salary",
                    amount = 5000000L,
                    date = "2025-04-25",
                    note = "April Salary"
                ),
                Transaction(
                    id = "3",
                    type = TransactionType.EXPENSE,
                    category = "Beverage",
                    amount = -15000L,
                    date = "2025-04-25",
                    note = "Coffee"
                )
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
                onTransactionAdded = { transaction: Transaction ->
                    transactions = transactions + transaction
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onLogin = { email, password ->
                    // Nanti bisa tambahkan logika auth / session
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                navController = navController,
                onRegister = { name, email, password ->
                    val success = UserStorage.register(email, password)
                    if (success) {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.REGISTER) { inclusive = true }
                        }
                    } else {
                        var errorMessage = "Email already registered."
                    }
                }
            )
        }

    }
}
