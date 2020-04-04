package com.thalita.movie_db_app.features.movies

import java.io.Serializable

class GetMovies :Serializable{
    var email: String?=null
        private set

    var posterPath: String?=null
        private set

    var movieID: Long?=null
        private set

    var titleMovie: String?=null
        private set

    var watchedMovie: Boolean?=null
        private set

    private constructor() {}
    constructor(email: String?, posterPath: String?, movieID: Long, titleMovie: String, watchedMovie: Boolean) {
        this.email=email
        this.posterPath=posterPath
        this.movieID=movieID
        this.titleMovie=titleMovie
        this.watchedMovie=watchedMovie
    }
}