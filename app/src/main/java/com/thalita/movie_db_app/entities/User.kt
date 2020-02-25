package com.thalita.movie_db_app.entities

import com.google.firebase.database.Exclude

class User{

    private var email: String? = null
    private var password: String? = null
    private var fullName: String? = null
    private var key: String? = null

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    @Exclude
    fun getPassword(): String? {
        return password
    }

    @Exclude
    fun setPassword(password: String?) {
        this.password = password
    }

    fun getFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String?) {
        this.fullName = fullName
    }

    fun getKey(): String? {
        return key
    }

    fun setKey(key: String?) {
        this.key = key
    }
}