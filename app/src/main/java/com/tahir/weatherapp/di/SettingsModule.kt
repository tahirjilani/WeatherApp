package com.tahir.weatherapp.di

import android.content.Context
import com.google.gson.Gson
import com.tahir.weatherapp.preference.AppPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsModule {

    @Singleton
    @Provides
    fun providesAppPreferences(applicationContext: Context): AppPreferences {
        return AppPreferences(applicationContext)
    }
}