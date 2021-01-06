package com.tahir.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.tahir.weatherapp.core.data.db.WeatherDao
import com.tahir.weatherapp.core.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides


/**
 * Module that provides database to Dagger for dependency injection.
 */
@Module
class TestDatabaseModule constructor(private val context:Context) {

    @Provides //scope is not necessary for parameters stored within the module
    fun context(): Context {
        return context
    }

    @Provides
    fun providesDatabase(context: Context): WeatherDatabase {
        return Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java).build()
    }


    @Provides
    fun providesAlbumDetailDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }




}