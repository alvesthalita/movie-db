package com.thalita.movie_db_app.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.entities.MovieResult
import com.thalita.movie_db_app.ui.activities.MovieDetailsActivity


class MovieListAdapter(private var context: Context, private var movieList: Array<MovieResult.MovieResponse>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(view.context).inflate(R.layout.cards_layout, view, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return movieList.size
    }
    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val posterURL= "https://image.tmdb.org/t/p/original" + movieList[position].poster_path
        val imageView = ImageView(context)

        Glide.with(context).load(posterURL).into(imageView)
        view.linearLayout.addView(imageView)
        view.movieTitle.text= movieList[position].original_title

        view.linearDetails.setOnClickListener { view: View? ->
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("movieDetails", movieList[position])
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linearLayout: LinearLayout= itemView.findViewById(R.id.linear_image)
        val linearDetails: LinearLayout= itemView.findViewById(R.id.linear_details)
        val movieTitle: TextView= itemView.findViewById(R.id.movieTitle)
    }
}
