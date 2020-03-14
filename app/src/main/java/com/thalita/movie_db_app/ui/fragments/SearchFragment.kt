package com.thalita.movie_db_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.core.service.ServiceVolley
import com.thalita.movie_db_app.interfaces.OnValidateUserEventListener
import com.thalita.movie_db_app.ui.adapters.MovieListAdapter
import com.thalita.movie_db_app.utils.LoadingProgressBar

class SearchFragment : Fragment(), OnValidateUserEventListener {

    private var searchView: SearchView?=null
    private var recyclerView: RecyclerView?=null
    private var loadingProgressBar: LoadingProgressBar?=null
    private var adapter: MovieListAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_search, container,false)
        init(view)
        return view
    }

    private fun init(view: View){
        searchView = view.findViewById(R.id.search_view)
        recyclerView = view.findViewById(R.id.recycler_view)
        searchView?.queryHint = "Pesquisar"
        searchView?.isFocusable = true
        loadingProgressBar=LoadingProgressBar(view)

        search()
    }

    private fun search(){
        searchView?.isIconified=false //Show the keyboard when the screen is open

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                loadingProgressBar!!.showProgressBar()
                searchMovie(query)
                return false
            }
        })

        searchView?.setOnCloseListener(SearchView.OnCloseListener {
            adapter?.clearSearch()
            searchView?.isIconified=false
            true
        })
    }

    private fun searchMovie(movieTitle: String) {
        val url = "https://api.themoviedb.org/3/search/movie?api_key=6d9583667c5dfe1cebfc99d3b6819c6b&language=pt-BR&query=" + movieTitle + "&page=1&include_adult=false"
        val serviceMovie = ServiceVolley(context, url)
        serviceMovie.setOnValidateRequestEventListener(this)
        serviceMovie.startRequest()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onValidateRequestSuccess(result: Array<MovieResult.MovieResponse>) {
        loadingProgressBar!!.hidePogressBar()
        recyclerView?.layoutManager = GridLayoutManager(context, 3)
        adapter =activity?.let { MovieListAdapter(it, result) }
        recyclerView!!.isClickable = true
        recyclerView?.adapter = adapter
    }

    override fun onValidateRequestFail(error: String?) {
        loadingProgressBar!!.hidePogressBar()
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}
