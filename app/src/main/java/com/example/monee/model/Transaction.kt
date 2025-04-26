package com.example.monee.ui.model

import org.threeten.bp.LocalDate

data class Transaction(
    val title: String,
    val amount: Int,
    val date: LocalDate = LocalDate.now()
)
