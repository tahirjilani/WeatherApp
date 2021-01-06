package com.tahir.weatherapp.utils

import kotlin.math.roundToInt

object TemperatureUtils {

    /**
     *  Get temperature in Celsius or Fahrenheit as rounded integer and unit
     */
    fun getTemperature(kelvin: Double, requiredCelsius: Boolean): String {
       return if (requiredCelsius){
           kelvinToCelsius(kelvin)
       }else {
           kelvinToFahrenheit(kelvin)
       }
    }

    private fun kelvinToCelsius(kelvin: Double): String {
        return (kelvin - 273.15).roundToInt().toString() + "°C"
    }

    private fun kelvinToFahrenheit(kelvin: Double): String {
        val f = (((kelvin - 273.15)*9/5) + 32).roundToInt()
        return "$f°F"
    }

}