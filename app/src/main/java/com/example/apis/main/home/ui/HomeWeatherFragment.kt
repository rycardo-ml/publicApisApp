package com.example.apis.main.home.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.apis.R
import com.example.apis.main.home.business.HomeWeatherViewModel
import com.example.apis.main.home.ui.adapter.WeatherAdapter
import com.example.apis.misc.livedata.ChangedItemType
import com.example.apis.misc.livedata.ListHolderType
import com.example.apis.misc.ui.CarouselRecyclerView
import com.example.apis.misc.ui.OrdinalSuperscriptFormatter
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "HomeWeatherFragment"
class HomeWeatherFragment: Fragment() {

    private val viewModel by viewModels<HomeWeatherViewModel>()

    private lateinit var tvLocation: TextView
    private lateinit var tvDate: TextView

    private lateinit var rvWeather: CarouselRecyclerView
    private val adapterWeather = WeatherAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_home_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLocation = view.findViewById(R.id.lyt_homeWeather_tv_location)
        tvDate = view.findViewById(R.id.lyt_homeWeather_tv_date)

        rvWeather = view.findViewById(R.id.lyt_homeWeather_crv_weather)
        rvWeather.initialize(adapterWeather)
        //rvWeather.setViewsToChangeColor(listOf(R.id.row_weather_iv, R.id.row_weather_tv))

        Log.d(TAG, "viewModel $viewModel")

        val spannableLocation = SpannableStringBuilder("Lisboa")
        addUnderscore(spannableLocation)
        tvLocation.text = spannableLocation

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEEE dd", Locale.getDefault())

        val spannableDate = OrdinalSuperscriptFormatter().format(sdf.format(calendar.time) + getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH)))
        addUnderscore(spannableDate)
        tvDate.text = spannableDate

        viewModel.init()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterObservers()
    }

    private fun registerObservers() {
        viewModel.getWeather().observe(viewLifecycleOwner, Observer {

            if (it.type == ListHolderType.FULL) {
                adapterWeather.updateItems(it.list)
                return@Observer
            }

            it.changedItems.forEach { item ->
                if (item.type == ChangedItemType.UPDATE) {
                    adapterWeather.notifyItemChanged(item.index)
                }
            }
        })
    }

    private fun unregisterObservers() {
        viewModel.getWeather().removeObservers(viewLifecycleOwner)
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
