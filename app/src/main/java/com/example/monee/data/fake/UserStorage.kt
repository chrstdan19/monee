package com.example.monee.data.fake

object UserStorage {
    private val users = mutableListOf<Pair<String, String>>()

    fun register(email: String, password: String): Boolean {
        if (users.any { it.first == email }) return false
        users.add(email to password)
        return true
    }

    fun login(email: String, password: String): Boolean {
        return users.any { it.first == email && it.second == password }
    }
}