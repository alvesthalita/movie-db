package com.thalita.movie_db_app.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.thalita.movie_db_app.R

class SearchFragment : Fragment() {

    private var searchView: SearchView?=null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_search, container,false)
        init(view)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(view: View){
        searchView = view.findViewById(R.id.search_view)
        searchView?.queryHint = "Pesquisar"
        searchView?.isFocusable = true

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                val string = query
                return false
            }

        })

    }

}