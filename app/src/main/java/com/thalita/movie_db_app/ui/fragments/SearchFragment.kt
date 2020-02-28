package com.thalita.movie_db_app.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.core.service.ServiceVolley
import com.thalita.movie_db_app.interfaces.OnValidateUserEventListener
import com.thalita.movie_db_app.ui.adapters.MovieListAdapter

class SearchFragment : Fragment(), OnValidateUserEventListener {

    private var recyclerView: RecyclerView?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_search, container,false)
        init(view)
        return view
    }

    @SuppressLint("WrongConstant")
    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)

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

    @SuppressLint("WrongConstant")
    override fun onValidateRequestSuccess(result: Array<MovieResult.MovieResponse>) {
        recyclerView?.layoutManager= GridLayoutManager(context, 3)
        val adapter =activity?.let { MovieListAdapter(it, result) }
        recyclerView!!.isClickable=true
        recyclerView?.adapter = adapter
    }

    override fun onValidateRequestFail(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}