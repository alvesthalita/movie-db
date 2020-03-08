package com.thalita.movie_db_app.utils

import android.view.View
import android.widget.ProgressBar
import com.thalita.movie_db_app.R

class LoadingProgressBar(var view: View) {

    private var progressBarStatus = 0
    var dummy:Int = 0

    fun showProgressBar(){
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

    fun hidePogressBar(){
        val progressBar =view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar?.visibility = View.GONE
    }

}