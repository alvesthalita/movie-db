package com.thalita.movie_db_app.ui.activities

import android.os.Build
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.entities.FavoriteMovie
import com.thalita.movie_db_app.ui.activities.generic.GenericActivity
import com.thalita.movie_db_app.utils.ConfigFirebase
import com.thalita.movie_db_app.utils.DateUtils
import com.thalita.movie_db_app.utils.LoadingProgressBar
import com.thalita.movie_db_app.utils.UserAuth


class MovieDetailsActivity : GenericActivity() {

    private lateinit var movieTitle: TextView
    private lateinit var movieDateRelease: TextView
    private lateinit var movieLanguage: TextView
    private lateinit var movieAverage: TextView
    private lateinit var moviePopular: TextView
    private lateinit var movieOverview: TextView
    private lateinit var favoriteMovie: CheckBox
    private lateinit var watchedMovie: CheckBox
    private lateinit var closeDetails: ImageView
    private lateinit var info: FavoriteMovie
    private lateinit var scrollView: ScrollView
    private lateinit var progressBar: LoadingProgressBar
    private var response: MovieResult.MovieResponse?= null
    private var databaseReference: DatabaseReference?=null
    private var userAuth: UserAuth?=null
    private var firebaseAuth: FirebaseAuth?=null
    private var favorite: FavoriteMovie?=null
    private var arrayList: ArrayList<FavoriteMovie>?=null

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
        watchedMovie = findViewById(R.id.checkbox_watched)
        closeDetails = findViewById(R.id.iv_details_close)
        scrollView = findViewById(R.id.scroll_details_movie)

//        progressBar = LoadingProgressBar()
        favorite = FavoriteMovie()
        userAuth = UserAuth(this)
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        response = intent.getSerializableExtra("movieDetails") as MovieResult.MovieResponse

        loadInformations()
        setMovieDetails()
        setMovieAsFavorite()
        setMovieAsWatched()
        setOnClickClose()
    }

    private fun setOnClickClose(){
        closeDetails.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMovieDetails() {
//        response = intent.getSerializableExtra("movieDetails") as MovieResult.MovieResponse
        val posterURL= "https://image.tmdb.org/t/p/w500" + response!!.poster_path
        val linearLayout = findViewById<LinearLayout>(R.id.linear_image)
        val imageView = ImageView(this)

        Glide.with(this).load(posterURL).into(imageView)
        linearLayout.addView(imageView)

        movieTitle.text = response!!.title
        movieDateRelease.text = DateUtils().stringToDate(response!!.release_date)

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
                favorite!!.setWatchedMovie(false)
                saveMovie(favorite!!)
            }
        }
    }

    private fun setMovieAsWatched(){
        watchedMovie.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                favorite!!.setEmail(userAuth!!.getEmail())
                favorite!!.setMovieID(response!!.id)
                favorite!!.setTitleMovie(response!!.title)
                favorite!!.setPosterPath(response!!.poster_path)
                favorite!!.setWatchedMovie(true)
                saveMovie(favorite!!)
            }
        }
    }

    private fun saveMovie(data: FavoriteMovie): Boolean {
        return try {
            databaseReference = ConfigFirebase().getFirebase()
            databaseReference!!.child("favorites").push().setValue(data)
            true
        } catch (e: java.lang.Exception) {
            Toast.makeText(
                applicationContext,
                "Não foi possível incluir na sua lista, tente novamente mais tarde",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
            false
        }
    }

    private fun loadInformations() {
        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference!!.child("favorites").orderByChild("email").equalTo("thalita@gmail.com")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        info =snapshot.getValue(FavoriteMovie::class.java)!!
                        arrayList?.addAll(listOf(info))

//                        if(response?.title.equals(info.getTitleMovie())) {
//                            watchedMovie.isChecked = true
//                        }
                    }

                    Log.d("arraysize: ", arrayList?.size.toString())
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

}
