package com.example.weather.ui.forecast.settingsRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Weather
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class Adapter(items: List<Weather>) : RecyclerView.Adapter<ViewHolder>() {

    var items = ArrayList(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.recycler_view_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateListOfItems(list: List<Weather>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}