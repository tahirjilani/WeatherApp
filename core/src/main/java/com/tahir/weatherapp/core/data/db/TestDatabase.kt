package com.tahir.weatherapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TestDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}