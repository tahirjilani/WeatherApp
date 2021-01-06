package com.tahir.weatherapp.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getHours(ms: Long): String?{

        val date =  Date(ms*1000L)
        return try {
            val resultFormat = SimpleDateFormat("ha", Locale.US)
            resultFormat.timeZone = Calendar.getInstance().timeZone

            resultFormat.format(date)
        }catch (ex: Exception){
            ex.printStackTrace()
            null
        }
    }

    fun getHourMinutes(ms: Long): String?{

        val date =  Date(ms*1000L)
        return try {
            val resultFormat = SimpleDateFormat("h:mm a", Locale.US)
            resultFormat.timeZone = Calendar.getInstance().timeZone

            resultFormat.format(date)
        }catch (ex: Exception){
            ex.printStackTrace()
            null
        }
    }

    fun getDate(ms: Long): String?{

        val date =  Date(ms*1000L)
        return try {
            val resultFormat = SimpleDateFormat("EEE, MMM d", Locale.US)
            resultFormat.timeZone = Calendar.getInstance().timeZone

            resultFormat.format(date)
        }catch (ex: Exception){
            ex.printStackTrace()
            null
        }
    }

    fun getDateTimeYear(ms: Long): String?{
        val date =  Date(ms*1000L)
        return try {
            val resultFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.US)
            resultFormat.timeZone = Calendar.getInstance().timeZone

            resultFormat.format(date)
        }catch (ex: Exception){
            ex.printStackTrace()
            null
        }
    }
}