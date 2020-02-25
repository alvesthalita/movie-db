package com.thalita.movie_db_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.core.service.ServiceVolley
import com.thalita.movie_db_app.interfaces.OnValidateUserEventListener
import com.thalita.movie_db_app.ui.adapters.MovieListAdapter

class SearchFragment : Fragment(), OnValidateUserEventListener {

    private var movieList: ListView?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_search, container,false)
        init(view)
        return view
    }

    private fun init(view: View) {
        //https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg - IMAGE URL
        movieList = view.findViewById(R.id.list_movie)
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
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val listItem: MovieResult.MovieResponse = movieList?.getItemAtPosition(position) as MovieResult.MovieResponse
    //            openDetails(listItem)
            }
    }

    override fun onValidateRequestFail(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
