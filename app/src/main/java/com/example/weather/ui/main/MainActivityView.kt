package com.example.weather.ui.main

interface MainActivityView {
    fun showCurrentWeather(temperature: Double, pressure: Int, humidity: Int,
                           speed: Double, deg: Double, main: String, country: String, city: String)

    fun showError(error: String?)
}