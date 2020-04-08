package com.example.weather.internet

import com.example.weather.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?units=metric&appid=39b26bdba8d1595289b541b253197efc")
    fun getCurrentWeather(@Query("lat") lat: String, @Query("lon") lon: String): Observable<WeatherResponse.Result>
}