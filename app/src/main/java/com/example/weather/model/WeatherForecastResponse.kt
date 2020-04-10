package com.example.weather.model

object WeatherForecastResponse {
    data class Result(val list: List<Information>, val city: City)
    data class Information(val main: Main, val dt_txt: String, val weather: List<Weather>)
    data class Main(val temp: Double)
    data class Weather(val main: String)
    data class City(val name: String)
}