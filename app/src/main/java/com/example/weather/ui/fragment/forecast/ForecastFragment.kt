package com.example.weather.ui.fragment.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Weather
import com.example.weather.ui.fragment.BaseFragment
import com.example.weather.ui.activity.MainActivity
import com.example.weather.ui.fragment.forecast.settingsRecyclerView.Adapter as myAdapter

class ForecastFragment : BaseFragment() {

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
        val listItems : View  = inflater.inflate(R.layout.fragment_forecast, container, false)

        val vRvWeather = listItems.findViewById<View>(R.id.vRvWeather) as RecyclerView


        vRvWeather.layoutManager = LinearLayoutManager(this.context)
        myAdapter = myAdapter(weather)
        vRvWeather.adapter = myAdapter

        return listItems
    }
}