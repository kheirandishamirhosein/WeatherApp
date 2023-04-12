package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class PersianCalendar(
    @Json(name = "date") val date: Date,
    @Json(name = "season") val season: Season,
    @Json(name = "time12") val time12: Time12,
    val timestamp: Timestamp,
    @Json(name = "timezone") val timezone: Timezone,
    val unix: Unix
)