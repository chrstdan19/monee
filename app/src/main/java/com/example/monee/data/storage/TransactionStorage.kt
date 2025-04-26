package com.example.monee.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import com.example.monee.model.Transaction


val Context.transactionDataStore: DataStore<List<Transaction>> by dataStore(
    fileName = "transactions.json",
    serializer = TransactionByteArraySerializer
)

// Serializer untuk menyimpan List<Transaction> ke file JSON
object TransactionByteArraySerializer : Serializer<List<Transaction>> {

    override val defaultValue: List<Transaction> = emptyList()

    override suspend fun readFrom(input: java.io.InputStream): List<Transaction> {
        return try {
            val jsonString = input.readBytes().decodeToString()
            if (jsonString.isBlank()) {
                emptyList()
            } else {
                Json.decodeFromString(ListSerializer(Transaction.serializer()), jsonString)
            }
        } catch (e: IOException) {
            emptyList() // return
        }
    }

    override suspend fun writeTo(t: List<Transaction>, output: java.io.OutputStream) {
        val jsonString = Json.encodeToString(ListSerializer(Transaction.serializer()), t)
        output.write(jsonString.encodeToByteArray())
    }
}