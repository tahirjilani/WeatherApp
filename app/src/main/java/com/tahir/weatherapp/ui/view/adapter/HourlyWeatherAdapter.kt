package com.tahir.weatherapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tahir.weatherapp.R
import com.tahir.weatherapp.core.domain.model.WeatherInfo
import com.tahir.weatherapp.utils.DateUtils
import com.tahir.weatherapp.utils.ImageUtils
import com.tahir.weatherapp.utils.TemperatureUtils

class HourlyWeatherAdapter constructor(
    private val items: List<WeatherInfo.WeatherSummary>, private val isCelsius: Boolean,
) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val timeTV: TextView = view.findViewById(R.id.timeTV)
        val iconIV: ImageView = view.findViewById(R.id.iconIV)
        val tempTV: TextView = view.findViewById(R.id.tempTV)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_weather_item, parent, false)
        return HourlyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = items[position]

        holder.timeTV.text = DateUtils.getHours(item.dt)
        holder.tempTV.text = TemperatureUtils.getTemperature(item.temp, isCelsius)
        holder.iconIV.apply {
            item.weather.let {
                if (!it.isNullOrEmpty()) {
                    val weather = it[0]
                    ImageUtils.load(this.context, this, weather.icon)
                }
            }

        }
    }

    override fun getItemCount() = items.size

}