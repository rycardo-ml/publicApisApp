package com.example.apis.main.home.ui.adapter

import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apis.R
import com.example.apis.main.home.business.WeatherCarousel
import com.example.apis.misc.ui.CarouselStatus
import com.example.apis.misc.ui.OrdinalSuperscriptFormatter
import java.text.SimpleDateFormat
import java.util.*

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

        private val viewLoading: View = itemView.findViewById(R.id.row_weather_lyt_loading)
        private val viewError: View = itemView.findViewById(R.id.row_weather_lyt_error)
        private val viewData: View = itemView.findViewById(R.id.row_weather_lyt_data)

        private val tvSource: TextView = itemView.findViewById(R.id.lyt_weather_tv_source)
        private val ivState: ImageView = itemView.findViewById(R.id.lyt_weather_iv_state)
        private val tvTemperature: TextView = itemView.findViewById(R.id.lyt_weather_tv_temperature)

        private val tvLocation: TextView = itemView.findViewById(R.id.lyt_weather_tv_location)
        private val tvDate: TextView = itemView.findViewById(R.id.lyt_weather_tv_date)

        fun bind(item: WeatherCarousel) {
            if (item.status == CarouselStatus.LOADING) {
                viewData.visibility = View.GONE
                viewError.visibility = View.GONE
                viewLoading.visibility = View.VISIBLE

                return
            }

            val weather = item.weather
            if (item.status == CarouselStatus.ERROR || weather == null) {
                viewData.visibility = View.GONE
                viewLoading.visibility = View.GONE
                viewError.visibility = View.VISIBLE

                return
            }

            tvSource.text = item.type.name
            tvTemperature.text = weather.temperature.toString()

            val spannableLocation = SpannableStringBuilder(weather.location)
            addUnderscore(spannableLocation)
            tvLocation.text = spannableLocation

            val calendar = Calendar.getInstance()
            calendar.time = weather.date

            val sdf = SimpleDateFormat("EEEE dd", Locale.getDefault())
            val spannableDate = OrdinalSuperscriptFormatter().format(sdf.format(calendar.time) + getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH)))
            addUnderscore(spannableDate)
            tvDate.text = spannableDate

            viewLoading.visibility = View.GONE
            viewError.visibility = View.GONE
            viewData.visibility = View.VISIBLE
        }

        private fun addUnderscore(spannable: SpannableStringBuilder) {
            spannable.setSpan(UnderlineSpan(), 0, spannable.toString().length, 0)
        }

        private fun getDayOfMonthSuffix(n: Int): String {
            return when (n) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }
}