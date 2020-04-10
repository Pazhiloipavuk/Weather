package com.example.weather.internet

import com.example.weather.model.CurrentWeatherResponse
import com.example.weather.model.WeatherForecastResponse
import io.reactivex.Observable

class WeatherRepository (private val service: WeatherService) {
    fun getCurrentWeather(lat: String, lon: String) : Observable<CurrentWeatherResponse.Result> {
        return service.getCurrentWeather(lat, lon)
    }

    fun getWeatherForecast(lat: String, lon: String) : Observable<WeatherForecastResponse.Result> {
        return service.getWeatherForecast(lat, lon)
    }
}