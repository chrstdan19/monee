package com.example.monee.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: String,
    val type: TransactionType,
    val category: String,
    val amount: Long,
    val date: String,
    val note: String? = null
)

@Serializable
enum class TransactionType {
    INCOME,
    EXPENSE
}
