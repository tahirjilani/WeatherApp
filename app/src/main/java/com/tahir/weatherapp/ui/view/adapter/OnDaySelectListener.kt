package com.tahir.weatherapp.ui.view.adapter

import com.tahir.weatherapp.core.domain.model.WeatherInfo

interface OnDaySelectListener {
    fun onDaySelected(dailySummary: WeatherInfo.DailySummary)
}