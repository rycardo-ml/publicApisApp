package com.example.apis.main.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apis.R
import com.example.apis.main.home.business.WeatherCarousel

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    private val items = mutableListOf<WeatherCarousel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_weather, parent, false)
        return WeatherHolder(row)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<WeatherCarousel>) {
        items.clear()
        items.addAll(newItems)

        notifyDataSetChanged()
    }

    inner class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WeatherCarousel) {

        }
    }
}