package com.example.weatherapp.data.model.forecastLocation

import com.squareup.moshi.Json

data class FiveForecastList(
    @Json(name = "clouds") val clouds: Clouds,
    @Json(name = "dt") val dt: Int,
    @Json(name = "dt_txt") val dt_txt: String,
    @Json(name = "main") val main: Main,
    //@Json(name = "pop") val pop: Double,
    //@Json(name = "visibility") val visibility: Int,
    @Json(name = "weather") val weather: List<Weather>,
)