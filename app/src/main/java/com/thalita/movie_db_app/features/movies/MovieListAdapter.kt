package com.thalita.movie_db_app.features.movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.thalita.movie_db_app.R
import com.thalita.movie_db_app.core.extension.loadFromUrl
import javax.inject.Inject

class MovieListAdapter
@Inject constructor(private var context: Context, private var movieList: Array<MovieResult.MovieResponse>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var arrayList: ArrayList<MovieResult.MovieResponse>?=null
    private var clearList: Boolean = false

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(view.context).inflate(R.layout.cards_layout, view, false)
        return ViewHolder(
            v
        )
    }
    override fun getItemCount(): Int {
        return if(clearList && arrayList!!.size == 0)
            arrayList!!.size
        else
            movieList.size
    }
    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val posterURL= if (movieList[position].poster_path.isNullOrEmpty()) "" else "https://image.tmdb.org/t/p/w500" + movieList[position].poster_path
        if (posterURL.isEmpty()) {
            view.posterImage.layoutParams.height = 200;
            view.posterImage.requestLayout()
            view.posterImage.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.unavailable_photo))
        } else view.posterImage.loadFromUrl(posterURL)

        view.movieTitle.text= movieList[position].title

        view.linearDetails.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("movieDetails", movieList[position])
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage: ImageView= itemView.findViewById(R.id.image_poster_movie)
        val linearDetails: LinearLayout= itemView.findViewById(R.id.linear_details)
        val movieTitle: TextView= itemView.findViewById(R.id.movieTitle)
    }

    fun clearSearch(){
        clearList = true
        arrayList = movieList.toCollection(ArrayList())
        arrayList?.clear()
        notifyDataSetChanged()
    }
}
