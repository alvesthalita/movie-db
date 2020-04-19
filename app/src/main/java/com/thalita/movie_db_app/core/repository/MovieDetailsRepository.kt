package com.thalita.movie_db_app.core.repository

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.thalita.movie_db_app.features.movies.MovieDetailsApiListener
import com.thalita.movie_db_app.features.movies.MovieDetailsResult

class MovieDetailsRepository(private var context: Context?, private var url: String?){

    fun startRequest(movieDetailsApiListener: MovieDetailsApiListener) {
        val queue= Volley.newRequestQueue(context)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->

            val data= response.toString()
            val result = Gson().fromJson(data, MovieDetailsResult::class.java)

            movieDetailsApiListener.onValidateRequestSuccess(result)

        }, Response.ErrorListener { error ->
            Log.d("ERROR: ", error.toString())
            movieDetailsApiListener.onValidateRequestFail("Não foi possível conectar com o servidor, tente novamente mais tarde.", true)
        })

        jsonObjectRequest.retryPolicy=DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES + 1,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(jsonObjectRequest)
    }
}