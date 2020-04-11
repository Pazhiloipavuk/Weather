package com.example.weather.ui.forecast

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Weather
import com.example.weather.ui.main.MainActivity
import com.example.weather.ui.forecast.settingsRecyclerView.Adapter as myAdapter

class ForecastFragment : Fragment() {

    private lateinit var myAdapter: myAdapter

    private var weather = ArrayList<Weather>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).currentCity.observe(viewLifecycleOwner, Observer {
            setTitle(it)
        })
        (activity as MainActivity).weatherForecast.observe(viewLifecycleOwner, Observer { weather ->
            myAdapter.updateListOfItems(weather)
        })
        var listItems : View  = inflater.inflate(R.layout.fragment_forecast, container, false)

        var vRvWeather = listItems.findViewById<View>(R.id.vRvWeather) as RecyclerView


        vRvWeather.layoutManager = LinearLayoutManager(this.context)
        myAdapter = myAdapter(weather)
        vRvWeather.adapter = myAdapter

        return listItems
    }

    private fun setTitle(title: String) {
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
}