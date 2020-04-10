package com.example.weather

import android.app.Application
import android.content.SharedPreferences
import com.example.weather.internet.ApiRest
import com.example.weather.internet.WeatherRepository
import com.example.weather.internet.WeatherService
import com.example.weather.storage.Storage
import io.realm.Realm
import io.realm.RealmConfiguration
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

class MyApp : Application() {

    companion object {
        lateinit var kodein: Kodein
    }

    override fun onCreate() {
        super.onCreate()

        kodein = Kodein {
            bind<Retrofit>() with singleton {
                ApiRest.getApi()
            }

            bind<WeatherRepository>() with singleton {
                WeatherRepository(instance<Retrofit>().create(WeatherService::class.java))
            }

            bind<Storage>() with singleton {
                Storage()
            }
        }
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}