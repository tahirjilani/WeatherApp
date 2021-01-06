package com.tahir.weatherapp.core.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tahir.weatherapp.core.domain.model.WeatherInfo

@Entity
data class WeatherEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "weather") val weatherInfo: WeatherInfo
)