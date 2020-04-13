package com.example.weather.ui.activity

import com.example.weather.model.Weather

interface MainActivityView {
    fun showCurrentWeather(weather: Weather)

    fun showError(error: String?)

    fun setWeatherForecast(city: String, weather: List<Weather>)
}