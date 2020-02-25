package com.thalita.movie_db_app.core

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.thalita.movie_db_app.interfaces.OnValidateUserEventListener
import org.json.JSONObject
import java.util.*

class ServiceMovie(context: Context?, url: String?){

    private var context: Context?=null
    private var url: String?=null
    private var validateUserListener: OnValidateUserEventListener?=null
    private var result: MovieResult?=null

    fun ServiceMovie(context: Context?, url: String?) {
        this.context=context
        this.url=url
    }

    fun startRequest() {
        val queue=Volley.newRequestQueue(context)
        val jsonObjectRequest=JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response: JSONObject ->
                try {
                    val data=response.toString()
                    result=Gson().fromJson(data, MovieResult::class.java)
                    if (result != null)
                    Objects.requireNonNull(result)?.response.let {
                        it?.let { it1 ->
                            validateUserListener?.onValidateRequestSuccess(
                                it1
                            )
                        }
                    }
                } catch (error: Exception) {
                    error.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                Log.d("ERROR RESPONSE: ", Objects.requireNonNull(error.message))
                validateUserListener?.onValidateRequestFail(error.message)
            }
        )
        jsonObjectRequest.retryPolicy=DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES + 1,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(jsonObjectRequest)
    }

    fun setOnValidateRequestEventListener(listener: OnValidateUserEventListener?) {
        validateUserListener=listener
    }
}