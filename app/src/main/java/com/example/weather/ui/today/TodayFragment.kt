package com.example.weather.ui.today

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weather.R
import com.example.weather.model.Weather
import com.example.weather.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_today.view.*


class TodayFragment : Fragment() {

    private lateinit var weather: Weather

    private var txtCityAndCountryText = ""
    private var txtTemperatureText = ""
    private var txtStatusText = ""
    private var txtHumidityText = ""
    private var txtPressureText = ""
    private var txtWindSpeedText = ""
    private var txtDirectionText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("CREATE")

        /*val nameObserver = Observer<Weather> { weather ->
            // Update the UI, in this case, a TextView.
            txtCityAndCountry.text = "${weather.city}, ${weather.country}"
            println(txtCityAndCountryText)
            txtTemperature.text = "${weather.temperature}"
            txtStatus.text = "${weather.main}"
            txtHumidity.text = "${weather.humidity}"
            txtPressure.text = "${weather.pressure}"
            txtWindSpeed.text = "${weather.speed}"
            txtDirection.text = "${weather.degrees}"
        }*/

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        //(activity as MainActivity).currentWeather.observe(this, nameObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setTitle("Today")
        var listItems : View  = inflater.inflate(R.layout.fragment_today, container, false)
        (activity as MainActivity).currentWeather.observe(viewLifecycleOwner, Observer { weather ->
            this.weather = weather
            txtCityAndCountry.text = "${weather.city}, ${weather.country}"
            txtTemperature.text = "${weather.temperature} ℃"
            txtStatus.text = "${weather.main}"
            txtHumidity.text = "${weather.humidity}%"
            txtPressure.text = "${weather.pressure} hPa"
            txtWindSpeed.text = "${weather.speed} km/h"
            txtDirection.text = "${weather.degrees}"
            println("${R.drawable.ic_cloud} ||||| ${weather.image}")
            imgWeather.setImageResource(weather.image)
        })

        var btnShare = listItems.findViewById<View>(R.id.btnShare) as Button
        initListener(btnShare)
        // Inflate the layout for this fragment
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putString("temperature", txtTemperature.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //textView = view.findViewById(R.id.text_test_today)
        //textView.text  = "Today"
        //this.view = vie

    }

    override fun onResume() {
        super.onResume()
        println("RESUME")

        //var weather = (activity as MainActivity).getWeather()

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun setTitle(title: String) {
        println("SETTITLE")
        (activity as AppCompatActivity?)!!.supportActionBar!!.setHomeButtonEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val textView = TextView(activity)
        textView.text = title
        textView.textSize = 20f
        textView.setTypeface(null, Typeface.NORMAL)
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.gravity = Gravity.CENTER
        textView.setTextColor(resources.getColor(R.color.colorWhite))
        (activity as AppCompatActivity?)!!.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        (activity as AppCompatActivity?)!!.supportActionBar!!.customView = textView
    }

    fun printHello(){
        println("JOPA")
    }

    override fun onPause() {
        super.onPause()
        println("PAUSE")

        txtCityAndCountryText = view!!.txtCityAndCountry.text.toString()
        txtTemperatureText = view!!.txtTemperature.text.toString()
        txtStatusText = view!!.txtStatus.text.toString()
        txtHumidityText = view!!.txtHumidity.text.toString()
        txtPressureText = view!!.txtPressure.text.toString()
        txtWindSpeedText = view!!.txtWindSpeed.text.toString()
        txtDirectionText = view!!.txtDirection.text.toString()
    }
}