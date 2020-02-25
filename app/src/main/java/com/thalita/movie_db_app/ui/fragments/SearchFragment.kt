package com.thalita.movie_db_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.core.service.ServiceVolley
import com.thalita.movie_db_app.interfaces.OnValidateUserEventListener
import com.thalita.movie_db_app.ui.activities.MovieDetailsActivity
import com.thalita.movie_db_app.ui.activities.SignInActivity
import com.thalita.movie_db_app.ui.adapters.MovieListAdapter
import java.util.*

class SearchFragment : Fragment(), OnValidateUserEventListener {

    private var movieList: ListView?=null
    private var edt_search: BootstrapEditText?=null
    private var btn_search: LinearLayout?=null
    private var searchMovie: Boolean=false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_search, container,false)
        init(view)
        return view
    }

    private fun init(view: View) {
        movieList = view.findViewById(R.id.list_movie)
        edt_search = view.findViewById(R.id.edt_search)
        btn_search = view.findViewById(R.id.linear_search)

        btn_search?.setOnClickListener {
//            searchMovie()
        }

        requestMovieList()
    }

    private fun requestMovieList() {
        val url = "https://api.themoviedb.org/3/movie/76341/similar?api_key=6d9583667c5dfe1cebfc99d3b6819c6b&language=pt-BR"
        val serviceMovie =
            ServiceVolley(context, url)
        serviceMovie.setOnValidateRequestEventListener(this)
        serviceMovie.startRequest()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onValidateRequestSuccess(result: Array<MovieResult.MovieResponse>) {
        val adapter = context?.let { MovieListAdapter(it, result) }
        movieList?.adapter = adapter

        movieList?.onItemClickListener =
            AdapterView.OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                val listItem: MovieResult.MovieResponse = movieList?.getItemAtPosition(position) as MovieResult.MovieResponse
                openDetails(listItem)
            }
    }

    override fun onValidateRequestFail(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun openDetails(item: MovieResult.MovieResponse) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("movieDetails", item)
        startActivity(intent)
    }

    private fun searchMovie(){
        searchMovie = true
        val searchmovie = edt_search?.text.toString()

        val url =
            "https://api.themoviedb.org/3/find/$searchmovie?api_key=6d9583667c5dfe1cebfc99d3b6819c6b&language=pt-BR&external_source=imdb_id"
        val serviceMovie =
            ServiceVolley(context, url)
        serviceMovie.setOnValidateRequestEventListener(this)
        serviceMovie.startRequest()
    }
}
