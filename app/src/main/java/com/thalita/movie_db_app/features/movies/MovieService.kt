package com.thalita.movie_db_app.features.movies

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class MovieService(private var context: Context?, private var url: String?){

    private var validateUserListener: MoviesApiListener?=null

    fun startRequest() {
        val queue= Volley.newRequestQueue(context)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->

                val data= response.toString()
                val result = Gson().fromJson(data, MovieResult::class.java)

                validateUserListener!!.onValidateRequestSuccess(result.response)

            }, Response.ErrorListener { error ->
                Log.d("VOLLEY ERROR: ", error.toString())
                validateUserListener!!.onValidateRequestFail("Não foi possível conectar com o servidor, tente novamente mais tarde.")
            })

        jsonObjectRequest.retryPolicy=DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES + 1,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(jsonObjectRequest)
    }

    fun setOnValidateRequestEventListener(listener: MoviesApiListener?) {
        validateUserListener = listener
    }
}