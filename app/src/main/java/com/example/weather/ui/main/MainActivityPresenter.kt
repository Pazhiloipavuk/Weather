package com.example.weather.ui.main

import com.example.weather.MyApp
import com.example.weather.internet.WeatherRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance

class MainActivityPresenter(private val mainActivity: MainActivityView) {
    private var disposable: Disposable? = null
    private var subscribe: Disposable? = null

    private val weatherService: WeatherRepository by MyApp.kodein.instance()

    fun getCurrentWeather(lat: String, lon: String) {
        disposable = weatherService.getCurrentWeather(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> mainActivity.showCurrentWeather(result.main.temp,
                    result.main.pressure, result.main.humidity, result.wind.speed,
                    result.wind.deg, result.weather[0].main, result.sys.country, result.name) },
                { error -> mainActivity.showError(error.message) }
            )
    }

    fun onPause() {
        disposable?.dispose()
        subscribe?.dispose()
    }
}