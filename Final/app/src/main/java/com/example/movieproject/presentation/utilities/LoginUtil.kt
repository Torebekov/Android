package com.example.movieproject.presentation.utilities

object LoginUtil {
    fun validateLoginForm(
        username: String,
        password: String
    ): Boolean {
        if(username.isEmpty() || password.isEmpty()) {
            return false
        }
        if(password.count { it.isDigit() } < 2) {
            return false
        }
        return true
    }
}