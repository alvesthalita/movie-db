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

    fun logout(){
        sharedPreferences.edit().clear().apply()
    }

//    private fun loadValues() {
//        EMAIL = loadValue(com.hdi.segurado.utils.general.Auth.types.cpfCnpj.name)
//        token=loadValue(com.hdi.segurado.utils.general.Auth.types.token.name)
//        pushToken=loadValue(com.hdi.segurado.utils.general.Auth.types.pushToken.name)
//        userName=loadValue(com.hdi.segurado.utils.general.Auth.types.userName.name)
//        password=loadValue(com.hdi.segurado.utils.general.Auth.types.password.name)
//        userPhoto=loadValue(com.hdi.segurado.utils.general.Auth.types.userPhoto.name)
//        useFacebook=loadValue(com.hdi.segurado.utils.general.Auth.types.useFacebook.name)
//    }
//
//    private fun loadValue(key: String): String? {
//        return if (sharedPreferences.contains(key)) {
////            crypto.decrypt(preferences.getString(key, ""))
//            sharedPreferences.getString(key, "")
//        } else null
//    }


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