package com.thalita.movie_db_app.core.plataform

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.thalita.movie_db_app.R

class SetMenuColor(private var context: Context, private var imageView: ImageView, private var textView: TextView) {

    @SuppressLint("ResourceType")
    fun setColor(){
        imageView.setColorFilter(ContextCompat.getColor(context, Color.parseColor(R.color.colorBlueLight.toString())))
        textView.setTextColor(Color.parseColor(R.color.colorBlueLight.toString()))
    }
}