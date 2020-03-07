package com.thalita.movie_db_app.core.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MovieResult : Serializable{
    class MovieResponse : Serializable {
        @SerializedName("id")
        val id: Int?=null

        @SerializedName("video")
        val video: String?=null

        @SerializedName("vote_count")
        val vote_count: String?=null

        @SerializedName("vote_average")
        val vote_average: String?=null

        @SerializedName("title")
        val title: String?=null

        @SerializedName("release_date")
        val release_date: String?=null

        @SerializedName("original_language")
        val original_language: String?=null

        @SerializedName("original_title")
        val original_title: String?=null

        @SerializedName("backdrop_path")
        val backdrop_path: String?=null

        @SerializedName("adult")
        val adult: String?=null

        @SerializedName("overview")
        val overview: String?=null

        @SerializedName("poster_path")
        val poster_path: String?=null

        @SerializedName("popularity")
        val popularity: String?=null

        @SerializedName("genre_ids")
        val genre_ids: List<*>?=null
    }

    @SerializedName("page")
    var page: String?=null

    @SerializedName("results")
    lateinit var response: Array<MovieResponse>

}