package com.example.weather.ui.fragment.forecast.settingsRecyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.model.Weather

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: Weather) {
        showItem(item)
    }

    abstract fun showItem(item: Weather)
}