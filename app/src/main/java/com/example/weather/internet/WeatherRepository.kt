package com.example.weather.internet

import com.example.weather.model.CurrentWeatherResponse
import com.example.weather.model.WeatherForecastResponse
import io.reactivex.Observable
import io.reactivex.Single

class WeatherRepository (private val service: WeatherService) {
    fun getCurrentWeather(lat: String, lon: String) : Single<CurrentWeatherResponse.Result> {
        return service.getCurrentWeather(lat, lon)
    }

    fun getWeatherForecast(lat: String, lon: String) : Single<WeatherForecastResponse.Result> {
        return service.getWeatherForecast(lat, lon)
    }
}