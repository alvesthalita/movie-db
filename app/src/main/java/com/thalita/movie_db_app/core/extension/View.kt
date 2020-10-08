package com.thalita.movie_db_app.core.extension

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.thalita.movie_db_app.R

fun View.cancelTransition() {
    transitionName = null
}

/**
 * Validate the visibility of the view
 */
fun View.isVisible() = this.visibility == View.VISIBLE

/**
 * Set the view as visible component
 */
fun View.visible() { this.visibility = View.VISIBLE }

/**
 * Set the view as invisible
 */
fun View.invisible() { this.visibility = View.GONE }

/**
 * Set the checkbox checked
 */
fun CheckBox.checked(){ this.isChecked = true}

/**
 * Set the checkbox unchecked
 */
fun CheckBox.unChecked(){ this.isChecked = false}

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
 * Show the message on the screen
 */
fun showToastMessage(activity: Activity, message: String){
    Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
}

/**
 * Shows the progress bar when is loading the items
 */
fun showProgressBar(view: View){
    var progressBarStatus = 0
    var dummy:Int = 0
    val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    progressBar.visible()

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
    progressBar?.invisible()
}