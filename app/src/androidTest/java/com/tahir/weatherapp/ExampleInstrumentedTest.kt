package com.tahir.weatherapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tahir.weatherapp.utils.TemperatureUtils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tahir.weatherapp", appContext.packageName)
    }

    @Test
    fun kelvinToCelsius_isCorrect() {

        val celsius = TemperatureUtils.getTemperature(291.0, true)
        assertEquals("18°C", celsius)
    }
}