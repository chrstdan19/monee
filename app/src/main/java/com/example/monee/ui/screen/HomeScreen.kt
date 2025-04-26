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
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.monee.ui.model.Transaction
import com.example.monee.ui.navigation.Routes

import java.text.NumberFormat
import java.util.Locale
import org.threeten.bp.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
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
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium
            )
            TransactionList(transactions)
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
        Transaction("Lunch", -25000),
        Transaction("Salary", 5000000),
        Transaction("Coffee", -15000)
    )

    val navController = rememberNavController()

    HomeScreen(
        navController = navController,
        transactions = dummyTransactions,
        onAddTransactionClick = {}
    )
}

