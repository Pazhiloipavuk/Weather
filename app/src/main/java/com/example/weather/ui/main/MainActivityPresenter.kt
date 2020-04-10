package com.example.weather.ui.main

import com.example.weather.MyApp
import com.example.weather.R
import com.example.weather.internet.WeatherRepository
import com.example.weather.model.Weather
import com.example.weather.storage.Storage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivityPresenter(private val mainActivity: MainActivityView) {
    private var disposable: Disposable? = null
    private var subscribe: Disposable? = null

    private val weatherService: WeatherRepository by MyApp.kodein.instance()

    private val weatherForecast = ArrayList<Weather>()

    private val storage: Storage by MyApp.kodein.instance()

    private var data = ""

    private var images = HashMap<String, Int>()

    private var currentTime = 0

    fun initMap() {
        images["Clear"] = R.drawable.ic_clear_sky_day
        images["Rain"] = R.drawable.ic_rain
        images["Mist"] = R.drawable.ic_mist
        images["Snow"] = R.drawable.ic_snow
        images["Clouds"] = R.drawable.ic_cloud
        images["Thunderstorm"] = R.drawable.ic_thunderstorm
        val sdf = SimpleDateFormat("HH")
        currentTime = sdf.format(Date()).toInt()
    }

    fun getCurrentWeather(lat: String, lon: String) {
        disposable = weatherService.getCurrentWeather(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> var weather = Weather()
                    println(result.main.temp)
                    weather.temperature = result.main.temp
                    weather.pressure = result.main.pressure
                    weather.humidity = result.main.humidity
                    weather.speed = result.wind.speed
                    weather.degrees = result.wind.deg
                    weather.main = result.weather[0].main
                    weather.country = result.sys.country
                    weather.city = result.name
                    println("before")
                    if (weather.main == "Clear" && (currentTime > 19 || currentTime < 8)) weather.image = R.drawable.ic_clear_sky_night
                    else weather.image = images[weather.main]!!
                    println("after")
                    mainActivity.showCurrentWeather(weather)
                    storage.deleteAll()
                    storage.addWeather(weather)},
                { error -> mainActivity.showError(error.message) }
            )
    }

    fun getWeatherForecast(lat: String, lon: String) {
        disposable = weatherService.getWeatherForecast(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> println(result.list.size)
                            for (weatherInformation in result.list) {
                                var weather = Weather()
                                weather.temperature = weatherInformation.main.temp
                                weather.main = weatherInformation.weather[0].main
                                var dataAndTime = weatherInformation.dt_txt.split(" ")
                                weather.data = dataAndTime[0]
                                weather.time = dataAndTime[1]
                                var hours = dataAndTime[1].split(":")[0].toInt()
                                if (weather.main == "Clear" && (hours > 19 || hours < 8)) weather.image = R.drawable.ic_clear_sky_night
                                else weather.image = images[weather.main]!!
                                if (dataAndTime[0] != data) {
                                    data = dataAndTime[0]
                                    var weatherIsHeader = Weather()
                                    weatherIsHeader.isHeader = true
                                    weatherIsHeader.data = data
                                    weatherForecast.add(weatherIsHeader)
                                }
                                weatherForecast.add(weather)
                            }
                            weatherForecast.forEach {storage.addWeather(it)}
                    mainActivity.setWeatherForecast(result.city.name, weatherForecast)
                    },
                { error -> mainActivity.showError(error.message) }
            )
    }

    fun getCurrentWeatherFromDB() = storage.getCurrentWeather()

    fun getWeatherForecastFromDB() = storage.getWeatherForecast()

    fun onPause() {
        disposable?.dispose()
        subscribe?.dispose()
    }
}