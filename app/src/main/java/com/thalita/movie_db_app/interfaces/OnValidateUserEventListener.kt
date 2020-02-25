package com.thalita.movie_db_app.interfaces

import com.thalita.movie_db_app.core.entities.MovieResult

interface OnValidateUserEventListener {
    fun onValidateRequestSuccess(result: Array<MovieResult.MovieResponse>)
    fun onValidateRequestFail(error: String?)
}