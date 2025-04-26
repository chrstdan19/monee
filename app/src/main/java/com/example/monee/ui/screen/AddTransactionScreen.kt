package com.example.monee.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.monee.ui.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    onTransactionAdded: (Transaction) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color(0xFFF8F5F0)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Type: ")
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = !isExpense,
                    onClick = { isExpense = false },
                    label = { Text("Income") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = isExpense,
                    onClick = { isExpense = true },
                    label = { Text("Expense") }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && amount.isNotBlank()) {
                        val transaction = Transaction(
                            title = title,
                            amount = if (isExpense) -amount.toInt() else amount.toInt()
                        )
                        onTransactionAdded(transaction)
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF655D3C)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Save Transaction",
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionPreview() {
    val navController = rememberNavController()
    AddTransactionScreen(
        navController = navController,
        onTransactionAdded = {}
    )
}
