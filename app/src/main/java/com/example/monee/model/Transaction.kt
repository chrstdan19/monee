package com.example.monee.ui.model

import org.threeten.bp.LocalDate

data class Transaction(
    val title: String,
    val amount: Int,
    val category: String,
    val date: LocalDate = LocalDate.now(),
    val note: String = ""
)
