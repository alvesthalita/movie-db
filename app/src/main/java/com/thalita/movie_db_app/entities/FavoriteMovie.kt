package com.thalita.movie_db_app.entities

class FavoriteMovie {

    private var email: String? = null
    private var titleMovie: String? = null
    private var posterPath: String? = null
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
}