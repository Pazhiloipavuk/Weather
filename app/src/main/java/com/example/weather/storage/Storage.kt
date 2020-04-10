package com.example.weather.storage

import com.example.weather.model.Weather
import io.realm.Realm

class Storage {
    private var realm = Realm.getDefaultInstance()

    fun getWeatherForecast() = realm.where(Weather::class.java).notEqualTo("data", "").findAll()!!

    fun getCurrentWeather() = realm.where(Weather::class.java).equalTo("data", "").findFirst()

    fun deleteAll() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

    fun addWeather(weather: Weather) {
        var id = 0
        if (realm.where(Weather::class.java).max("id")?.toInt() != null) {
            id = realm.where(Weather::class.java).max("id")!!.toInt() + 1
        }
        weather.id = id

        try {
            realm.beginTransaction()
            realm.copyToRealm(weather)
            realm.commitTransaction()
        } catch (e: Exception) {
            println(e)
        }
    }
}