package com.thalita.movie_db_app.features.movies

class FavoriteMovie {

    private var userID: FavoriteMovie? = null
    private var email: String? = null
    private var titleMovie: String? = null
    private var posterPath: String? = null
    private var movieID: Int? = null
    private var watchedMovie: Boolean? = null
    private var favorite: Boolean? = null

    fun getUserID(): FavoriteMovie? {
        return userID
    }

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

    fun getWatchedMovie(): Boolean? {
        return watchedMovie
    }

    fun setWatchedMovie(watchedMovie: Boolean?) {
        this.watchedMovie = watchedMovie
    }

    fun getFavorite(): Boolean? {
        return favorite
    }

    fun setFavorite(favorite: Boolean?) {
        this.favorite = favorite
    }
}