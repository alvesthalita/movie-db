package com.thalita.movie_db_app.core.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.thalita.movie_db_app.R

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

/**
 * Load the image poster from URL
 */
fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

/**
 * Shows the progress bar when is loading the items
 */
fun showProgressBar(view: View){
    var progressBarStatus = 0
    var dummy:Int = 0
    val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    progressBar.visibility = View.VISIBLE

    Thread(Runnable {
        while (progressBarStatus < 100) {
            try {
                dummy+=25
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            progressBarStatus = dummy
            progressBar.progress = progressBarStatus
        }

    }).start()
}

/**
 * Hide the progress bar after the loading finished
 */
fun hidePogressBar(view: View){
    val progressBar =view.findViewById<ProgressBar>(R.id.progressBar)
    progressBar?.visibility = View.GONE
}