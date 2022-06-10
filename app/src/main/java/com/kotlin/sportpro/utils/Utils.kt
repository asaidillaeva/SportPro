package com.kotlin.sportpro.utils

import android.content.Context
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Utils(private val context: Context? = null) {

    fun convertDate(date: String): String {
        var formattedDate = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val parsedDate =
                LocalDateTime.parse("2018-12-14T09:55:00", DateTimeFormatter.ISO_DATE_TIME)
            formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

        } else {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            formattedDate = formatter.format(parser.parse(date))
        }
        return formattedDate
    }



    companion object {
        fun of(context: Context): Utils {
            return Utils()
        }
    }
}
