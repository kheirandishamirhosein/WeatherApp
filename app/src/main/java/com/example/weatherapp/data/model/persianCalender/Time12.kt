package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Time12(
    @Json(name = "full") val full: FullX,
    val hour: Hour,
    val microsecond: Microsecond,
    val minute: Minute,
    val second: Second,
    val shift: Shift
)