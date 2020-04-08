package com.example.weather.ui.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_today.*

class MainActivity : AppCompatActivity(), MainActivityView {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var presenter = MainActivityPresenter(this)

    companion object {
        const val PERMISSION_ID = 42
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.navigationView)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_today,
            R.id.navigation_forecast
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        println("------------------------$isConnected")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            fusedLocationClient?.lastLocation?.
                addOnSuccessListener(this){location : Location? ->
                    if(location != null) {
                            println("${location.latitude}")
                            println("${location.longitude}")
                        presenter.getCurrentWeather(location.latitude.toString(), location.longitude.toString())
                    } else {
                        println("Cannot to get location")
                    }
                }
        }
    }

    override fun showCurrentWeather(temperature: Double, pressure: Int, humidity: Int,
                                    speed: Double, deg: Double, main: String, country: String, city: String) {
        println("$temperature $pressure ---------------------------")
        txtCityAndCountry.text = "$city, $country"
        txtTemperature.text = "$temperature ℃"
        txtStatus.text = "$main"
        txtHumidity.text = "$humidity%"
        txtPressure.text = "$pressure hPa"
        txtWindSpeed.text = "$speed km/h"
        txtDirection.text = "$deg"
    }

    override fun showError(error: String?){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        println("$error-----------------------------------")
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
            PERMISSION_ID -> getCurrentLocation()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
