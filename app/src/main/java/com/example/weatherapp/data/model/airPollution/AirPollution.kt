package com.example.weatherapp.data.model.airPollution

import com.squareup.moshi.Json

data class AirPollution(
    @Json(name = "coord") val coord: Coord,
    @Json(name = "list") val list: List<AirPollutionList>
)