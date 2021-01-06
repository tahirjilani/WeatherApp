package com.tahir.weatherapp.ui.vm

import androidx.lifecycle.ViewModel
import com.tahir.weatherapp.preference.AppPreferences
import com.tahir.weatherapp.utils.TemperatureUtils

abstract class SettingsViewModel constructor(private val appPreferences: AppPreferences) : ViewModel() {

    fun getTemperature(kelvin: Double): String {
        val isCelsius = appPreferences.isCelsius()
        return TemperatureUtils.getTemperature(kelvin, isCelsius)
    }

    fun getIsCelsius(): Boolean {
        return appPreferences.isCelsius()
    }

    fun setIsCelsius(isCelsius: Boolean){
        appPreferences.setIsCelsius(isCelsius)
    }
}