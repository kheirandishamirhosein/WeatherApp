package com.example.weatherapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

/*
* For to format date and time
* */
object DateFormatter {
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("yyyy/MM/dd")
    val currentDate: String = formatter.format(Date())
}

object TimeFormatter {
    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("HH:mm aaa")
    val currentTime: String = formatter.format(Date())
}