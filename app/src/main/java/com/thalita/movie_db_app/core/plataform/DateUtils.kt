package com.thalita.movie_db_app.core.plataform

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat

class DateUtils {

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToDate(dateString: String?): String {
        val parser=SimpleDateFormat("yyyy-MM-dd")
        val formatter=SimpleDateFormat("dd/MM/yyyy")
        return  formatter.format(parser.parse(dateString))
    }
}