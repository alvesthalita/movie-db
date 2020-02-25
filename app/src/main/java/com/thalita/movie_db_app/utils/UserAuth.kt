package com.thalita.movie_db_app.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class UserAuth(context: Context){

    private val preferencesName = "app.preferences.movie"
    private lateinit var editor: Editor
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    private val EMAIL = "LOGGED_USER_EMAIL"
    private val PASSWORD = "LOGGED_USER_PASSWORD"
    private val USER_LOOGED = "LOGGED_USER"

    fun saveUser(email: String?, password: String?, userLogged: Boolean?) {
        editor = sharedPreferences.edit()

        editor.putString(EMAIL, email)
        editor.putString(PASSWORD, password)
        editor.putBoolean(USER_LOOGED, userLogged!!)
        editor.apply()
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(EMAIL, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(PASSWORD, null)
    }
    
    fun getUserLogged(): Boolean?{
        return sharedPreferences.getBoolean(USER_LOOGED, false)
    }

}