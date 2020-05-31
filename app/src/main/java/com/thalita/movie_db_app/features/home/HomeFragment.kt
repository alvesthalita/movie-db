package com.thalita.movie_db_app.features.home

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.hidePogressBar
import com.thalita.movie_db_app.core.extension.showProgressBar
import com.thalita.movie_db_app.core.repository.MovieRepository
import com.thalita.movie_db_app.features.movies.MovieListAdapter
import com.thalita.movie_db_app.features.movies.MovieResult
import com.thalita.movie_db_app.features.movies.MoviesApiListener
import java.util.*


class HomeFragment : Fragment(),
    MoviesApiListener {

    private var recyclerView: RecyclerView?=null
    private var rootView: View?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container,false)
        init(view)
        return view
    }

    private fun init(view: View) {
        rootView = view
        recyclerView = view.findViewById(R.id.recycler_view)
        showProgressBar(rootView!!)
        requestMovieList()
    }

    private fun requestMovieList() {
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=6d9583667c5dfe1cebfc99d3b6819c6b&language=pt-BR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1"

        val movieRepository =
            MovieRepository(
                context,
                url
            )
        movieRepository.startRequest(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onValidateRequestSuccess(result: Array<MovieResult.MovieResponse>) {
        hidePogressBar(rootView!!)
        recyclerView?.layoutManager = GridLayoutManager(context, 3)
        val adapter =activity?.let {
            MovieListAdapter(
                it,
                Arrays.copyOf(result, result.size - 2)
            )
        }
        recyclerView!!.isClickable = true
        recyclerView?.adapter = adapter
    }

    override fun onValidateRequestFail(message: String?, error: Boolean) {
        hidePogressBar(rootView!!)

        if (error) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}