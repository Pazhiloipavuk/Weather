package com.example.weather.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Weather : RealmObject() {

    @PrimaryKey
    var id = 0

    var temperature: Double = 0.0
    var pressure: Int = 0
    var humidity: Int = 0
    var speed: Double = 0.0
    var degrees: Double = 0.0
    var image: Int = 0
    var country: String = ""
    var city: String = ""
    var main: String = ""
    var data: String = ""
    var time: String = ""
    var isHeader: Boolean = false
}