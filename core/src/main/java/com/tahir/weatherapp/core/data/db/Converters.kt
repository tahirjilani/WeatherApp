package com.tahir.weatherapp.core.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tahir.weatherapp.core.domain.model.WeatherInfo

/**
 * To save time lets use Gson to convert WeatherInfo
 */
class Converters {
    @TypeConverter
    fun fromWeatherInfo(value: WeatherInfo): String {
        val gson = Gson()
        val type = object : TypeToken<WeatherInfo>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeatherInfo(value: String): WeatherInfo {
        val gson = Gson()
        val type = object : TypeToken<WeatherInfo>() {}.type
        return gson.fromJson(value, type)
    }
}