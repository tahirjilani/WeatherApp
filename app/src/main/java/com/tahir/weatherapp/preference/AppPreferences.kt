package com.tahir.weatherapp.preference

import android.content.Context

class AppPreferences constructor(private val applicationContext: Context) {

    private val preferences = applicationContext.getSharedPreferences("app-prefs", Context.MODE_PRIVATE)

    fun setIsCelsius(isCelsius: Boolean){
        with (preferences.edit()) {
            putBoolean("isCelsius", isCelsius)
            commit()
        }
    }

    fun isCelsius(): Boolean {
        return preferences.getBoolean("isCelsius", false)
    }
}