package com.example.weather.internet

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.XMLFormatter

class ApiRest {
    companion object {

        private var BASE_URL = "http://api.openweathermap.org/"

        fun getApi(): Retrofit {

            val builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)

            return builder.build()
        }
    }
}