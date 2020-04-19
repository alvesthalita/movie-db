package com.thalita.movie_db_app.features.movies

interface MovieDetailsApiListener {
    fun onValidateRequestSuccess(result: MovieDetailsResult)
    fun onValidateRequestFail(message: String?, error: Boolean?)
}