package com.tahir.weatherapp.core

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tahir.weatherapp.core.data.db.Converters
import com.tahir.weatherapp.core.data.db.TestDatabase
import com.tahir.weatherapp.core.data.db.WeatherDao
import com.tahir.weatherapp.core.data.db.WeatherEntity
import com.tahir.weatherapp.core.domain.model.Resource
import com.tahir.weatherapp.core.domain.model.WeatherInfo
import com.tahir.weatherapp.core.utils.TestUtils
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Test to verify if WeatherInfo is being read properly after writing into the database, using our
 * Converters
 */
@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var testDatabase: TestDatabase
    private lateinit var converters: Converters

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        testDatabase = Room.inMemoryDatabaseBuilder(context, TestDatabase::class.java).build()
        weatherDao = testDatabase.weatherDao()
        converters = Converters()
    }


    @Test
    @Throws(Exception::class)
    fun writeWeatherAndReadToVerify() {

        val timeZone = "Asia/Dubai"

        val weatherInfo: WeatherInfo = TestUtils.createWeather(timeZone, converters)
        weatherDao.insert(WeatherEntity(1, weatherInfo))

        val cachedList = weatherDao.getAll()
        if (!cachedList.isNullOrEmpty()){
            val cachedWeather = cachedList[0].weatherInfo
            assertEquals(timeZone, cachedWeather.timezone)
        }else{
            fail("there is something wrong with database implementation")
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        testDatabase.close()
    }

}