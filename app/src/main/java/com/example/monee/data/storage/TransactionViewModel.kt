package com.example.monee.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monee.model.Transaction
import com.example.monee.data.transactionDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(app: Application) : AndroidViewModel(app) {
    private val dataStore = app.transactionDataStore

    val transactions: Flow<List<Transaction>> = dataStore.data

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            val currentList = dataStore.data.stateIn(this).value ?: emptyList()
            val updatedList = currentList + transaction
            dataStore.updateData { updatedList }
        }
    }

    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            val currentList = dataStore.data.stateIn(this).value ?: emptyList()
            val updatedList = currentList.filterNot { it.id == id }
            dataStore.updateData { updatedList }
        }
    }
}