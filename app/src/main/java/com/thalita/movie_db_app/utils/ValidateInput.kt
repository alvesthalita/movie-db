package com.thalita.movie_db_app.utils

class ValidateInput{

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}