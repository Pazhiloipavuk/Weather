package com.example.weather.ui.forecast.settingsRecyclerView

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.model.Weather
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_today.view.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: Weather) {
        showTask(item)
    }

    private fun showTask(item: Weather) {
        if (item.isHeader) {
            itemView.imgStatus.visibility = View.INVISIBLE
            itemView.txtTemperatureTime.visibility = View.INVISIBLE
            itemView.txtWeatherStatus.visibility = View.INVISIBLE
            itemView.txtTime.gravity = View.TEXT_ALIGNMENT_CENTER
            itemView.txtTime.text = item.data
        } else {
            itemView.txtTime.text = "${item.time}"
            itemView.txtWeatherStatus.text = "${item.main}"
            itemView.txtTemperatureTime.text = "${item.temperature}"
            itemView.imgStatus.setImageResource(item.image)
        }
    }
}