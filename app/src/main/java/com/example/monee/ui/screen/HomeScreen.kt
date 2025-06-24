package com.example.monee.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monee.data.fake.UserStorage
import com.example.monee.model.Transaction
import com.example.monee.ui.component.TransactionChartWithLegend

import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    transactions: List<Transaction>,
    onAddTransactionClick: () -> Unit,
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
    val userName = UserStorage.currentUserName ?: "User"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello, $userName 👋", fontSize = 18.sp) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransactionClick) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            BalanceCard(totalBalance = transactions.sumOf { it.amount }.toInt())
            Spacer(modifier = Modifier.height(16.dp))

            TransactionChartWithLegend(
                incomeAmount = transactions.filter { it.amount > 0 }.sumOf { it.amount }.toFloat(),
                expenseAmount = transactions.filter { it.amount < 0 }.sumOf { kotlin.math.abs(it.amount) }.toFloat()
            )

            Spacer(modifier = Modifier.height(16.dp))
            SummaryByCategory(transactions)

            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            TransactionList(transactions)
        }
    }
}

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
                    text = "Rp $totalBalance",
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
                    text = transaction.note ?: transaction.id,
                    fontSize = 14.sp
                )
                Text(
                    text = "Rp $amountFormatted",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = amountColor
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = transaction.date,
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
        Transaction(
            id = "1",
            type = com.example.monee.model.TransactionType.EXPENSE,
            category = "Food",
            amount = -25000,
            date = "26 Apr 2025",
            note = "Lunch with friends"
        ),
        Transaction(
            id = "2",
            type = com.example.monee.model.TransactionType.INCOME,
            category = "Salary",
            amount = 5000000,
            date = "25 Apr 2025",
            note = "April Salary"
        ),
        Transaction(
            id = "3",
            type = com.example.monee.model.TransactionType.EXPENSE,
            category = "Coffee",
            amount = -15000,
            date = "25 Apr 2025",
            note = "Kopi senja"
        )
    )

    MaterialTheme {
        HomeScreenContent(
            transactions = dummyTransactions,
            onAddTransactionClick = {}
        )
    }
}