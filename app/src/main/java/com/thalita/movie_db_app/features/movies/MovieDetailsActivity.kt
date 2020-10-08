package com.thalita.movie_db_app.features.movies

import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.thalita.movie_db_app.core.extension.*
import com.thalita.movie_db_app.core.plataform.BaseActivity
import com.thalita.movie_db_app.core.plataform.ConfigFirebase
import com.thalita.movie_db_app.core.plataform.DateUtils
import com.thalita.movie_db_app.core.plataform.UserAuth
import com.thalita.movie_db_app.core.repository.MovieDetailsRepository
import java.util.*
import kotlin.concurrent.schedule
import com.thalita.movie_db_app.R

class MovieDetailsActivity : BaseActivity(), MovieDetailsApiListener{

    private lateinit var movieTitle: TextView
    private lateinit var movieDateRelease: TextView
    private lateinit var movieLanguage: TextView
    private lateinit var movieAverage: TextView
    private lateinit var moviePopular: TextView
    private lateinit var movieOverview: TextView
    private lateinit var favoriteMovie: CheckBox
    private lateinit var watchedMovie: CheckBox
    private lateinit var closeDetails: ImageView
    private lateinit var scrollView: ScrollView
    private lateinit var imagePoster: ImageView
    private var response: MovieResult.MovieResponse?= null
    private var responseAdapter: GetMovies?= null
    private var movieID: Int?=null
    private var movieName: String?=null
    private var moviePosterPath: String?=null
    private var databaseReference: DatabaseReference?=null
    private var userAuth: UserAuth?=null
    private var firebaseAuth: FirebaseAuth?=null
    private var favorite: FavoriteMovie?=null
    private var favoritesList: MutableList<FavoriteMovie>?=null
    private var updateMovie: MutableList<FavoriteMovie>?=null

    override fun setLayout() {
        hideTop(true)
        setContentView(R.layout.activity_movie_details)
    }

    override fun getObjects() {

    }

    override fun setObjects() {
        val rootView = window.decorView.rootView
        showProgressBar(rootView)
        init()
    }

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
        imagePoster = findViewById(R.id.image_poster_movie)

        favorite =FavoriteMovie()
        userAuth =UserAuth(this)
        firebaseAuth = ConfigFirebase().getFirebaseAuth()
        favoritesList = mutableListOf()
        updateMovie = mutableListOf()

        Timer("SettingUp", false).schedule(1000) {
            getMoviesDetails()
        }

        setCheckBoxAction()
        setOnClickClose()
    }

    private fun getMoviesDetails(){
        if(intent.getSerializableExtra("movieID") != null) {
            response =intent.getSerializableExtra("movieID") as MovieResult.MovieResponse
            movieID = response!!.id
            movieName = response!!.title
            moviePosterPath = response!!.poster_path
        }else if(intent.getSerializableExtra("movieDetailsAdapter") != null){
            responseAdapter=intent.getSerializableExtra("movieDetailsAdapter") as GetMovies
            movieID = responseAdapter?.movieID
            movieName = responseAdapter!!.titleMovie
            moviePosterPath = responseAdapter!!.posterPath
        }

        val url: String = "https://api.themoviedb.org/3/movie/" + movieID.toString() +"?api_key=6d9583667c5dfe1cebfc99d3b6819c6b&language=pt-BR";
        val movieRepository =MovieDetailsRepository(applicationContext, url)
        movieRepository.startRequest(this)
    }

    private fun setOnClickClose(){
        closeDetails.setOnClickListener {
            finish()
        }
    }

    private fun setCheckBoxAction(){
        favoriteMovie.setOnClickListener { _ ->
            if(favoriteMovie.isChecked) {
                setMovieAsFavorite(isFavorite = true)
            }else{
                setMovieAsFavorite(isFavorite = false)
            }
        }

        watchedMovie.setOnClickListener { _ ->
            if(watchedMovie.isChecked) {
                setMovieAsWatched(isWatched = true)
            }else{
                setMovieAsWatched(isWatched = false)
            }
        }
    }

    private fun setMovieAsFavorite(isFavorite: Boolean){
        var isOnTheList= false

        for (i in favoritesList!!.indices){
            if(favoritesList!![i].getMovieID() == movieID){
                isOnTheList = true
                val key = favoritesList!![i].getKey().toString()
                val reference: DatabaseReference=ConfigFirebase().getFirebase()!!.child("favorites").child(key)
                reference.child("favorite").setValue(isFavorite)
                break
            }
        }

        if(!isOnTheList){
            favorite!!.setEmail(userAuth!!.getEmail())
            favorite!!.setMovieID(movieID)
            favorite!!.setTitleMovie(movieName)
            favorite!!.setPosterPath(moviePosterPath)
            favorite!!.setWatchedMovie(false)
            favorite!!.setFavorite(isFavorite)
            saveMovie(favorite!!)
        }
    }

    private fun setMovieAsWatched(isWatched: Boolean){
        var isOnTheList= false

        for (i in favoritesList!!.indices){
            if(favoritesList!![i].getMovieID() == movieID){
                isOnTheList = true
                val key = favoritesList!![i].getKey().toString()
                val reference: DatabaseReference=ConfigFirebase().getFirebase()!!.child("favorites").child(key)
                reference.child("watchedMovie").setValue(isWatched)
                break
            }
        }

        if(!isOnTheList){
            favorite!!.setEmail(userAuth!!.getEmail())
            favorite!!.setMovieID(movieID)
            favorite!!.setTitleMovie(movieName)
            favorite!!.setPosterPath(moviePosterPath)
            favorite!!.setWatchedMovie(isWatched)
            favorite!!.setFavorite(false)
            saveMovie(favorite!!)
        }
    }

    private fun saveMovie(data: FavoriteMovie): Boolean {
        return try {
            databaseReference = ConfigFirebase().getFirebase()
            databaseReference!!.child("favorites").push().setValue(data)
            true
        } catch (e: java.lang.Exception) {
            showToastMessage(this, "Não foi possível incluir na sua lista, tente novamente mais tarde")
            e.printStackTrace()
            false
        }
    }

    /**
     * Exibe se o filme já foi assistido/favoritado.
     */
    private fun markAsFavoriteAndWatchedMovie() {
        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference!!.child("favorites").orderByChild("email").equalTo(userAuth?.getEmail())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val movie =snapshot.getValue(FavoriteMovie::class.java)!!
                        movie.setKey(snapshot.key.toString())
                        favoritesList?.add(movie)

                        if(movieID.toString() == movie.getMovieID().toString()) {
                            if(movie.getFavorite() as Boolean) {
                                favoriteMovie.checked()
                            }

                            if(movie.getWatchedMovie() as Boolean){
                                watchedMovie.checked()
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    watchedMovie.unChecked()
                    favoriteMovie.unChecked()
                }
            })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onValidateRequestSuccess(result: MovieDetailsResult) {
        markAsFavoriteAndWatchedMovie()
        val rootView = window.decorView.rootView
        hidePogressBar(rootView)
        scrollView.visible()
        val posterURL= if (result.backdrop_path.isNullOrEmpty()) "" else "https://image.tmdb.org/t/p/w500" + result.backdrop_path
        if (posterURL.isEmpty()) imagePoster.setImageDrawable(getDrawable(R.drawable.unavailable_photo)) else imagePoster.loadFromUrl(posterURL)

        movieTitle.text = if (result.title.isNullOrEmpty()) getString(R.string.no_content) else result.title
        movieDateRelease.text = DateUtils().stringToDate(result.release_date)

        movieLanguage.text = result.languages[0].name

        movieAverage.text = if (result.vote_average.isNullOrEmpty()) getString(R.string.no_content) else result.vote_average
        moviePopular.text = if (result.popularity.isNullOrEmpty()) getString(R.string.no_content) else result.popularity
        movieOverview.text = if(result.overview.isNullOrEmpty()) getString(R.string.no_content) else result.overview
    }

    override fun onValidateRequestFail(message: String?, error: Boolean?) {
        finish()
        showToastMessage(this, message!!)
    }

}
