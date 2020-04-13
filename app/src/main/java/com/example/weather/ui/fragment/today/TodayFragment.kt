package com.example.weather.ui.fragment.today

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.example.weather.R
import com.example.weather.model.Weather
import com.example.weather.ui.fragment.BaseFragment
import com.example.weather.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_today.*


class TodayFragment : BaseFragment() {

    private lateinit var weather: Weather

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setTitle("Today")
        val listItems : View  = inflater.inflate(R.layout.fragment_today, container, false)
        (activity as MainActivity).currentWeather.observe(viewLifecycleOwner, Observer { weather ->
            this.weather = weather
            txtCityAndCountry.text = "${weather.city}, ${weather.country}"
            txtTemperature.text = "${weather.temperature} ℃"
            txtStatus.text = weather.main
            txtHumidity.text = "${weather.humidity}%"
            txtPressure.text = "${weather.pressure} hPa"
            txtWindSpeed.text = "${weather.speed} km/h"
            txtDirection.text = "${weather.degrees} °"
            imgWeather.setImageResource(weather.image)
        })

        val btnShare = listItems.findViewById<View>(R.id.btnShare) as Button
        initListener(btnShare)
        return listItems
    }

    private fun initListener(button: Button) {
        button.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Temperature: ${weather.temperature}℃\n" +
                        "Pressure: ${weather.pressure} hPa \n" +
                        "Humidity: ${weather.humidity}% \n" +
                        "Wind speed: ${weather.speed} km/h\n" +
                        "Direction of the wind: ${weather.degrees}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}