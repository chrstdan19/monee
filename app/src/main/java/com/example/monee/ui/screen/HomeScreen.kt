package com.example.monee.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.monee.ui.model.Transaction
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalDate

import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    transactions: List<Transaction>,
    onAddTransactionClick: () -> Unit
) {
    HomeScreenContent(
        transactions = transactions,
        onAddTransactionClick = onAddTransactionClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    transactions: List<Transaction>,
    onAddTransactionClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello, Daniel 👋", fontSize = 18.sp) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddTransactionClick() }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            BalanceCard(totalBalance = transactions.sumOf { it.amount })
            Spacer(modifier = Modifier.height(20.dp))
            SummaryByCategory(transactions)
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium
            )
            TransactionList(transactions)
        }
    }
}

// Summary Category
@Composable
fun SummaryByCategory(transactions: List<Transaction>) {
    if (transactions.isNotEmpty()) {
        val categorySummary = transactions
            .groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val topCategory = categorySummary.maxByOrNull { kotlin.math.abs(it.value) }

        topCategory?.let { (category, total) ->
            val amountFormatted = NumberFormat.getNumberInstance(Locale("in", "ID"))
                .format(kotlin.math.abs(total))

            val summaryText = if (total < 0) {
                "You spent Rp $amountFormatted on $category."
            } else {
                "You earned Rp $amountFormatted from $category."
            }

            Text(
                text = summaryText,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
}


@Composable
fun TransactionList(transactions: List<Transaction>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
fun BalanceCard(totalBalance: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF655D3C))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total Balance",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp ${totalBalance}",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    val amountFormatted = NumberFormat.getNumberInstance(Locale("in", "ID"))
        .format(kotlin.math.abs(transaction.amount))

    val amountColor = if (transaction.amount < 0) Color.Red else Color(0xFF2E7D32)

    val formattedDate = transaction.date.format(
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF6F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = transaction.category,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transaction.title,
                    fontSize = 14.sp
                )
                Text(
                    text = "Rp $amountFormatted",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = amountColor
                )
            }

            // Notes per transaction
            if (transaction.note.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = transaction.note,
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = formattedDate,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyTransactions = listOf(
        Transaction("Lunch", -25000, "Food", date = LocalDate.of(2025, 4, 26)),
        Transaction("Salary", 5000000, "Salary", date = LocalDate.of(2025, 4, 26)),
        Transaction("Coffee", -15000, "Food", date = LocalDate.of(2025, 4, 26))
    )

    MaterialTheme {
        HomeScreenContent(
            transactions = dummyTransactions,
            onAddTransactionClick = {}
        )
    }
}
