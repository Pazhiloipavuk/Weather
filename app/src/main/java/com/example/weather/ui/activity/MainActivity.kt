package com.example.weather.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weather.R
import com.example.weather.model.Weather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    MainActivityView {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var presenter =
        MainActivityPresenter(this)

    val currentWeather: MutableLiveData<Weather> by lazy {
        MutableLiveData<Weather>()
    }

    val currentCity: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val weatherForecast: MutableLiveData<List<Weather>> by lazy {
        MutableLiveData<List<Weather>>()
    }

    companion object {
        const val PERMISSION_ID = 42
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initBottomNavigation()
        initSwipeRefreshListener()
        getCurrentWeatherAndWeatherForecastFromDB()
        getCurrentLocation()
    }

    private fun initSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener {
            getCurrentLocation()
        }
    }

    private fun initBottomNavigation() {
        val navView: BottomNavigationView = findViewById(R.id.navigationView)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_today,
            R.id.navigation_forecast
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun getCurrentWeatherAndWeatherForecastFromDB() {
        val weather= presenter.getCurrentWeatherFromDB()
        if (weather != null) {
            currentWeather.value = weather
            currentCity.value = weather.city
        }
        else return

        val weatherForecastFromDB: List<Weather> = presenter.getWeatherForecastFromDB()

        if (weatherForecastFromDB.isNotEmpty()) {
            weatherForecast.value = weatherForecastFromDB
        }
        else return
    }

    private fun getCurrentLocation() {
        swipeRefreshLayout.isRefreshing = true
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (!isConnected) showError("Couldn't connect to network")
        else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            if (checkPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                fusedLocationClient?.lastLocation?.
                addOnSuccessListener(this){location : Location? ->
                    if(location != null) {
                        presenter.getCurrentWeather(location.latitude.toString(), location.longitude.toString())
                        presenter.getWeatherForecast(location.latitude.toString(), location.longitude.toString())
                    } else {
                        showError("Couldn't get location")
                    }
                }
            }
        }
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showCurrentWeather(weather: Weather) {
        currentWeather.value = weather
    }

    override fun setWeatherForecast(city: String, weather: List<Weather>) {
        currentCity.value = city
        weatherForecast.value = weather
    }

    override fun showError(error: String?){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun checkPermission(vararg perm:String) : Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(this,it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            if(perm.toList().any {
                    ActivityCompat.
                        shouldShowRequestPermissionRationale(this, it)}
            ) {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("Permission needed!")
                    .setPositiveButton("OK"){_, _ ->
                        ActivityCompat.requestPermissions(this, perm,
                            PERMISSION_ID
                        )
                    }
                    .setNegativeButton("No"){_, _ -> }
                    .create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(this, perm,
                    PERMISSION_ID
                )
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_ID -> {
                getCurrentLocation()}
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
