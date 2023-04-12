package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Date(
    @Json(name = "day") val day: Day,
    @Json(name = "full") val full: Full,
    @Json(name = "month") val month: Month,
    @Json(name = "weekday") val weekday: Weekday,
    @Json(name = "year") val year: Year
)