package com.thalita.movie_db_app.ui.activities

import android.os.Build
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity
import com.thalita.movie_db_app.utils.DateUtils

class MovieDetailsActivity : GenericActivity() {

    private lateinit var movieTitle: TextView
    private lateinit var movieDateRelease: TextView
    private lateinit var movieLanguage: TextView
    private lateinit var movieAverage: TextView
    private lateinit var moviePopular: TextView
    private lateinit var movieOverview: TextView
    private lateinit var favoriteMovie: CheckBox

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_movie_details)
    }

    override fun getObjects() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setObjects() {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        movieTitle = findViewById(R.id.tv_movie_title)
        movieDateRelease = findViewById(R.id.tv_movie_date)
        movieLanguage = findViewById(R.id.tv_movie_language)
        movieAverage = findViewById(R.id.tv_movie_average)
        moviePopular = findViewById(R.id.tv_movie_popular)
        movieOverview = findViewById(R.id.tv_movie_overview)
        favoriteMovie = findViewById(R.id.checkbox_favorite)

        setMovieDetails()
//        setMovieAsFavorite()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMovieDetails() {
        val response: MovieResult.MovieResponse = intent.getSerializableExtra("movieDetails") as MovieResult.MovieResponse
        val posterURL= "https://image.tmdb.org/t/p/original" + response.poster_path
        val linearLayout = findViewById<LinearLayout>(R.id.linear_image)
        val imageView = ImageView(this)

        Glide.with(this).load(posterURL).into(imageView)
        linearLayout.addView(imageView)

        movieTitle.text = response.title
        movieDateRelease.text =DateUtils().stringToDate(response.release_date).toString()

        if(response.original_language == "en"){
            movieLanguage.text = "Inglês"
        }else{
            movieLanguage.text = response.original_language
        }

        movieAverage.text = response.vote_average
        moviePopular.text = response.popularity
        movieOverview.text = response.overview
    }

    private fun setMovieAsFavorite(){
        favoriteMovie.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                favoriteMovie.text = "Adicinado aos favoritos"
            }else{
                favoriteMovie.text = "Marcar como favorito"
            }
        }
    }
}
