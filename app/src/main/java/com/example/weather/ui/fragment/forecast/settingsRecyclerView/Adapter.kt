package com.example.weather.ui.fragment.forecast.settingsRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Weather

class Adapter(items: List<Weather>) : RecyclerView.Adapter<BaseViewHolder>() {

    private var items = ArrayList(items)

    companion object {
        private const val HEADER_VIEW_TYPE = 0
        private const val WEATHER_FORECAST_VIEW_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            HEADER_VIEW_TYPE -> {
                return HeaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_view_item_header,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return WeatherForecastViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_view_item_weather_forecast,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isHeader) HEADER_VIEW_TYPE
        else WEATHER_FORECAST_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateListOfItems(list: List<Weather>) {
        items.clear()
        notifyItemRangeRemoved(0, itemCount)
        items.addAll(list)
        notifyDataSetChanged()
    }
}