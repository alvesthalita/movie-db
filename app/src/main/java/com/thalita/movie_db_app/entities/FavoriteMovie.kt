package com.thalita.movie_db_app.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class FavoriteMovie : Serializable{

    @SerializedName("email")
    private var email: String? = null

    @SerializedName("titleMovie")
    private var titleMovie: String? = null

    @SerializedName("posterPath")
    private var posterPath: String? = null

    @SerializedName("movieID")
    private var movieID: Int? = null

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getTitleMovie(): String? {
        return titleMovie
    }

    fun setTitleMovie(titleMovie: String?) {
        this.titleMovie = titleMovie
    }

    fun getPosterPath(): String? {
        return posterPath
    }

    fun setPosterPath(porterPath: String?) {
        this.posterPath = porterPath
    }

    fun getMovieID(): Int? {
        return movieID
    }

    fun setMovieID(movieID: Int?) {
        this.movieID = movieID
    }

    var response: FavoriteMovie?=null
}