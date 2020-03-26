package com.thalita.movie_db_app.core.plataform

class ValidateInput{

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}