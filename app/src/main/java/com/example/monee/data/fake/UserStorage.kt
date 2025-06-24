package com.example.monee.data.fake

object UserStorage {
    private var users = mutableMapOf<String, Pair<String, String>>()
    var currentUserName: String? = null

    fun register(email: String, password: String, name: String): Boolean {
        if (users.containsKey(email)) return false
        users[email] = password to name
        return true
    }

    fun login(email: String, password: String): Boolean {
        val user = users[email]
        return if (user != null && user.first == password) {
            currentUserName = user.second
            true
        } else {
            false
        }
    }
}