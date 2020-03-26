package com.thalita.movie_db_app.features.movies

class FavoriteMovieResult {

    private var key: String?=null
    private lateinit var value: Array<FavoriteMovieData>

    fun FavoriteMovieResult() {}

    fun FavoriteMovieResult(key: String?, value: Array<FavoriteMovieData>) {
        this.key=key
        this.value=value
    }

    fun getkey(): String? {
        return key
    }

    fun getSubclass(): Array<FavoriteMovieData>? {
        return value
    }

//    private var key: String?=null
//
//    fun setKey(key: String?) {
//        this.key=key
//    }
//
//    fun getKey(): String? {
//        return key
//    }

    class FavoriteMovieData {
        private var email: String?=null
        private var titleMovie: String?=null
        private var posterPath: String?=null
        private var movieID: Int?=null

        fun getEmail(): String? {
            return email
        }

        fun setEmail(email: String?) {
            this.email=email
        }

        fun getTitleMovie(): String? {
            return titleMovie
        }

        fun setTitleMovie(titleMovie: String?) {
            this.titleMovie=titleMovie
        }

        fun getPosterPath(): String? {
            return posterPath
        }

        fun setPosterPath(porterPath: String?) {
            this.posterPath=porterPath
        }

        fun getMovieID(): Int? {
            return movieID
        }

        fun setMovieID(movieID: Int?) {
            this.movieID=movieID
        }
    }
}