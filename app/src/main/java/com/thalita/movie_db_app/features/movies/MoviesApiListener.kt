package com.thalita.movie_db_app.features.movies

interface MoviesApiListener {
    fun onValidateRequestSuccess(result: Array<MovieResult.MovieResponse>)
    fun onValidateRequestFail(error: String?)
}