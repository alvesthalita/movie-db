package com.thalita.movie_db_app.features.movies

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MovieDetailsResult : Serializable{

    class MovieDetailsGenres : Serializable {
        @SerializedName("id")
        val id: Int?=null

        @SerializedName("name")
        val name: String?=null
    }


    class MovieDetailsLanguages : Serializable {
        @SerializedName("iso_639_1")
        val iso_639_1: String?=null

        @SerializedName("name")
        val name: String?=null
    }

    @SerializedName("adult")
    val adult: Boolean?=null

    @SerializedName("backdrop_path")
    val backdrop_path: String?=null

    @SerializedName("budget")
    val budget: Int?=null

    @SerializedName("genres")
    lateinit var genres: Array<MovieDetailsGenres>

    @SerializedName("homepage")
    val homepage: String?=null

    @SerializedName("id")
    val id: Int?=null

    @SerializedName("title")
    val title: String?=null

    @SerializedName("release_date")
    val release_date: String?=null

    @SerializedName("overview")
    val overview: String?=null

    @SerializedName("vote_average")
    val vote_average: String?=null

    @SerializedName("popularity")
    val popularity: String?=null

    @SerializedName("spoken_languages")
    lateinit var languages: Array<MovieDetailsLanguages>
}