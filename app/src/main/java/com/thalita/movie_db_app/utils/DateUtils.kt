package com.thalita.movie_db_app.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

class DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToDate(dateString: String?): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return dateString?.format(formatter).toString()
    }
}