package com.thalita.movie_db_app.ui.activities

import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.entities.FavoriteMovie
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity
import com.thalita.movie_db_app.utils.ConfigFirebase
import com.thalita.movie_db_app.utils.DateUtils
import com.thalita.movie_db_app.utils.UserAuth

class MovieDetailsActivity : GenericActivity() {

    private lateinit var movieTitle: TextView
    private lateinit var movieDateRelease: TextView
    private lateinit var movieLanguage: TextView
    private lateinit var movieAverage: TextView
    private lateinit var moviePopular: TextView
    private lateinit var movieOverview: TextView
    private lateinit var favoriteMovie: CheckBox
    private var response: MovieResult.MovieResponse?= null
    private var databaseReference: DatabaseReference?=null
    private var userAuth: UserAuth?=null
    private var firebaseAuth: FirebaseAuth?=null
    private var favorite: FavoriteMovie?=null

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

        favorite = FavoriteMovie()
        userAuth = UserAuth(this)
        firebaseAuth = ConfigFirebase().getFirebaseAuth()

        setMovieDetails()
        setMovieAsFavorite()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMovieDetails() {
        response = intent.getSerializableExtra("movieDetails") as MovieResult.MovieResponse
        val posterURL= "https://image.tmdb.org/t/p/w500" + response!!.poster_path
        val linearLayout = findViewById<LinearLayout>(R.id.linear_image)
        val imageView = ImageView(this)

        Glide.with(this).load(posterURL).into(imageView)
        linearLayout.addView(imageView)

        movieTitle.text = response!!.title
        movieDateRelease.text =DateUtils().stringToDate(response!!.release_date).toString()

        if(response!!.original_language == "en"){
            movieLanguage.text = "Inglês"
        }else{
            movieLanguage.text = response!!.original_language
        }

        movieAverage.text = response!!.vote_average
        moviePopular.text = response!!.popularity
        movieOverview.text = response!!.overview
    }

    private fun setMovieAsFavorite(){
        favoriteMovie.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                favorite!!.setEmail(userAuth!!.getEmail())
                favorite!!.setMovieID(response!!.id)
                favorite!!.setTitleMovie(response!!.title)
                favorite!!.setPosterPath(response!!.poster_path)
                saveAsFavorite(favorite!!)
            }else{
//                favoriteMovie.text = "Marcar como favorito"
            }
        }
    }

    private fun saveAsFavorite(favorite: FavoriteMovie): Boolean {
        return try {
            databaseReference = ConfigFirebase().getFirebase()
            databaseReference!!.child("favorites").push().setValue(favorite)
            true
        } catch (e: java.lang.Exception) {
            Toast.makeText(
                applicationContext,
                "Não foi possível realizar o cadastro, tente novamente mais tarde",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
            false
        }
    }

}
