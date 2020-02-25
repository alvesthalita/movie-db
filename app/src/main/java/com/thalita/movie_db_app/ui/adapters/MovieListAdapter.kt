package com.thalita.movie_db_app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.androidquery.AQuery
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult

class MovieListAdapter(private var context: Context, private var movieList: Array<MovieResult.MovieResponse>) : BaseAdapter() {

    override fun getCount(): Int {
        return movieList.size
    }

    override fun getItem(position: Int): Any? {
        return movieList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var convertView= convertView

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_movie_list, parent, false)
        }

        val aq = AQuery(convertView)
        val item: MovieResult.MovieResponse = movieList[position]
        aq.id(R.id.tv_movie_title).text(item.title)
        return convertView!!
    }
}