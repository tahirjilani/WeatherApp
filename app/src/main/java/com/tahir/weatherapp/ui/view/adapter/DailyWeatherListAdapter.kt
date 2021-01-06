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

class DailyWeatherListAdapter constructor(
    private val items: List<WeatherInfo.DailySummary>,
    private val isCelsius: Boolean,
    private val listener: OnDaySelectListener
    ): RecyclerView.Adapter<DailyWeatherListAdapter.DayViewHolder>() {

    class DayViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val timeTV: TextView = view.findViewById(R.id.timeTV)
        val iconIV: ImageView = view.findViewById(R.id.iconIV)
        val tempTV: TextView = view.findViewById(R.id.tempTV)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_weather_item, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = items[position]

        holder.timeTV.text = DateUtils.getDate(item.dt)

        val minTemp = TemperatureUtils.getTemperature(item.temp.min, isCelsius)
        val maxTemp = TemperatureUtils.getTemperature(item.temp.max, isCelsius)
        val temperature = "$minTemp / $maxTemp"

        holder.tempTV.text = temperature
        holder.iconIV.apply {
            item.weather.let {
                if (!it.isNullOrEmpty()) {
                    val weather =  it[0]
                    ImageUtils.load(this.context, this, weather.icon)
                }
            }
        }
        holder.view.setOnClickListener {
            listener.onDaySelected(item)
        }
    }

    override fun getItemCount() = items.size

}