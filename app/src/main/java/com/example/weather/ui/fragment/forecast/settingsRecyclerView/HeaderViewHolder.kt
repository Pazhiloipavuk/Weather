package com.example.weather.ui.fragment.forecast.settingsRecyclerView

import android.view.View
import com.example.weather.model.Weather
import com.example.weather.ui.fragment.forecast.settingsRecyclerView.BaseViewHolder
import kotlinx.android.synthetic.main.recycler_view_item_header.view.*

class HeaderViewHolder(view: View) : BaseViewHolder(view) {
    override fun showItem(item: Weather) {
        itemView.txtDate.text = item.data
    }
}