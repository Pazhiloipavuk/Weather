package com.example.weather.model

object CurrentWeatherResponse {
    data class Result(val main: Main, val wind: Wind, val sys: Sys, val name: String, val weather: List<Weather>)
    data class Main(val temp: Double, val pressure: Int, val humidity: Int)
    data class Wind(val speed: Double, val deg: Double)
    data class Sys(val country: String)
    data class Weather(val main: String)
}