package com.example.weatherapp.util

import java.math.RoundingMode
import kotlin.math.roundToInt

/*
* For convert kelvin to celsius and celsius to fahrenheit
* */
object KelvinToCelsius {
    fun kelvinToCelsius(temp: Double): Double {
        var celsius = temp
        celsius = celsius.minus(273)
        return celsius.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }
}

object CelsiusToFahrenheit {
    fun celsiusToFahrenheit(temp: Double): Double {
        var celsius = temp
        celsius = celsius.minus(273)
        val kelvin = celsius.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        return kelvin.times(1.8).plus(32).roundToInt().toDouble()
    }
}