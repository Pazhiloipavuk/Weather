package com.example.weather.internet

import com.example.weather.model.WeatherResponse
import io.reactivex.Observable

class WeatherRepository (private val service: WeatherService) {
    fun getCurrentWeather(lat: String, lon: String) : Observable<WeatherResponse.Result> {
        return service.getCurrentWeather(lat, lon)
    }
}