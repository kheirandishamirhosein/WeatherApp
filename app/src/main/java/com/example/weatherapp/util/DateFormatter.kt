package com.example.weatherapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateFormatter {

    // var current: String ?= null
    @SuppressLint("SimpleDateFormat")
    fun dateFormat(date: String){
        /*
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val current = LocalDate.now().format(formatter)
        */
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val  current = formatter.format(Date())


    }
}