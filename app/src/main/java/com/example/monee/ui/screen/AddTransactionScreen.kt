package com.example.monee.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.monee.model.Transaction
import com.example.monee.model.TransactionType
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    onTransactionAdded: (Transaction) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }
    var note by remember { mutableStateOf("") }

    val categories = listOf("Food", "Gift", "Salary", "Transport", "Shopping")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

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
            // Input Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input Amount
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Input Note
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note (optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Select Type (Income / Expense)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Type:")
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

            // Select Category
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Category:")
                val chunkedCategories = categories.chunked(3)
                chunkedCategories.forEach { rowItems ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        rowItems.forEach { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category },
                                label = { Text(category) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    if (title.isNotBlank() && amount.isNotBlank()) {
                        val transaction = Transaction(
                            id = System.currentTimeMillis().toString(), // Generate ID dari timestamp
                            type = if (isExpense) TransactionType.EXPENSE else TransactionType.INCOME,
                            category = selectedCategory,
                            amount = if (isExpense) -amount.toLong() else amount.toLong(),
                            date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), // Tanggal hari ini
                            note = note
                        )
                        onTransactionAdded(transaction)
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF655D3C)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Save Transaction", color = Color.White)
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