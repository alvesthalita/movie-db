package com.thalita.movie_db_app.features.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.loadFromUrl
import javax.inject.Inject
import kotlin.properties.Delegates

class MyListAdapter
    @Inject constructor(private var context: Context, private var movieList: MutableList<FavoriteMovie>) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(view.context).inflate(R.layout.cards_layout, view, false)
        return ViewHolder(
            v
        )
    }
    override fun getItemCount(): Int {
        return movieList.size
    }
    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val posterURL= "https://image.tmdb.org/t/p/original" + movieList[position].getPosterPath()
        view.posterImage.loadFromUrl(posterURL)
        view.movieTitle.text= movieList[position].getTitleMovie()

//        view.linearDetails.setOnClickListener {
//            val intent = Intent(context, MovieDetailsActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//            intent.putExtra("movieDetails", movieList[position])
//            context.startActivity(intent)
//        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage: ImageView= itemView.findViewById(R.id.image_poster_movie)
        val linearDetails: LinearLayout= itemView.findViewById(R.id.linear_details)
        val movieTitle: TextView= itemView.findViewById(R.id.movieTitle)

    }
}