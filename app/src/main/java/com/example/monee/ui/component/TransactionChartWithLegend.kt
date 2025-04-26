package com.example.monee.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TransactionChartWithLegend(
    incomeAmount: Float,
    expenseAmount: Float,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TransactionPieChart(
            incomeAmount = incomeAmount,
            expenseAmount = expenseAmount
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LegendItem(color = Color(0xFF81C784), label = "Income")
            LegendItem(color = Color(0xFFE57373), label = "Expense")
        }
    }
}

@Composable
fun TransactionPieChart(
    incomeAmount: Float,
    expenseAmount: Float,
    modifier: Modifier = Modifier
) {
    val total = incomeAmount + expenseAmount
    if (total <= 0f) return

    val incomeSweep = (incomeAmount / total) * 360f
    val expenseSweep = (expenseAmount / total) * 360f

    Canvas(
        modifier = modifier
            .size(200.dp)
            .padding(8.dp)
    ) {
        // Expense slice (merah)
        drawArc(
            color = Color(0xFFE57373),
            startAngle = 0f,
            sweepAngle = expenseSweep,
            useCenter = true,
            size = Size(size.width, size.height)
        )

        // Income slice (hijau)
        drawArc(
            color = Color(0xFF81C784),
            startAngle = expenseSweep,
            sweepAngle = incomeSweep,
            useCenter = true,
            size = Size(size.width, size.height)
        )
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionChartPreview() {
    TransactionChartWithLegend(
        incomeAmount = 700000f,
        expenseAmount = 300000f
    )
}