package com.example.weather.ui.forecast.settingsRecyclerView

import android.view.View
import com.example.weather.model.Weather
import kotlinx.android.synthetic.main.recycler_view_item_weather_forecast.view.*

class WeatherForecastViewHolder(view: View) : BaseViewHolder(view) {

    override fun showItem(item: Weather) {
        itemView.txtTime.text = "${item.time}"
        itemView.txtWeatherStatus.text = "${item.main}"
        itemView.txtTemperatureTime.text = "${item.temperature}"
        itemView.imgStatus.setImageResource(item.image)
    }
}