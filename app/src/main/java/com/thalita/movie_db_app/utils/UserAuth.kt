package com.thalita.movie_db_app.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class UserAuth(private var context: Context){

    private var sharedPreferences: SharedPreferences? = null
    private var editor: Editor? = null

    private val EMAIL = "LOGGED_USER_EMAIL"
    private val PASSWORD = "LOGGED_USER_PASSWORD"

    fun Preferences(context: Context) {
        this.context = context
        val NOME_ARQUIVO = "app.preferences"
        sharedPreferences =
            context.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE)
//        editor = sharedPreferences.edit()
    }

    fun saveUser(email: String?, password: String?) {
        editor!!.putString(EMAIL, email)
        editor!!.putString(PASSWORD, password)
        editor!!.commit()
    }

    fun getEmail(): String? {
        return sharedPreferences!!.getString(EMAIL, null)
    }

    fun getPassword(): String? {
        return sharedPreferences!!.getString(PASSWORD, null)
    }

}