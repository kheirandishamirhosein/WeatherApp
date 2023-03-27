package com.example.weatherapp.data.model.airPollution

import com.squareup.moshi.Json

data class AirPollutionList(
    @Json(name = "components") val components: Components,
    //@Json(name = "dt") val dt: Int,
    @Json(name = "main") val main: Main
)