package com.thalita.movie_db_app.entities

import com.google.firebase.database.Exclude

class User{

    private var email: String? = null
    private var senha: String? = null
    private var nome: String? = null
    private var key: String? = null

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    @Exclude
    fun getSenha(): String? {
        return senha
    }

    @Exclude
    fun setSenha(senha: String?) {
        this.senha = senha
    }

    fun getNome(): String? {
        return nome
    }

    fun setNome(nome: String?) {
        this.nome = nome
    }

    fun getKey(): String? {
        return key
    }

    fun setKey(key: String?) {
        this.key = key
    }
}