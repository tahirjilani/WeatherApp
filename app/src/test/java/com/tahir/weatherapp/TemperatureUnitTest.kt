package com.tahir.weatherapp

import com.tahir.weatherapp.utils.TemperatureUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TemperatureUnitTest {
    @Test
    fun kelvinToCelsiusConversion_isCorrect() {
        val kelvin = 291.0
        val celsius = TemperatureUtils.getTemperature(kelvin, true)
        println("$kelvin kelvin to celsius is: $celsius")

        assertEquals("18°C", celsius)
    }

    @Test
    fun kelvinToFahrenheitConversion_isCorrect() {
        val kelvin = 291.0
        val fahrenheit = TemperatureUtils.getTemperature(kelvin, false)
        println("$kelvin kelvin to fahrenheit is: $fahrenheit")

        assertEquals("64°F", fahrenheit)
    }
}