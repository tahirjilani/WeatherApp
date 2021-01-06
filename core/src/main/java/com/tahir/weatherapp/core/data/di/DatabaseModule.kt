package com.tahir.weatherapp.core.data.di;


import android.content.Context
import androidx.room.Room
import com.tahir.weatherapp.core.data.db.WeatherDao
import com.tahir.weatherapp.core.data.db.WeatherDatabase
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): WeatherDatabase {

        return Room.databaseBuilder(context,
            WeatherDatabase::class.java, "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }

}