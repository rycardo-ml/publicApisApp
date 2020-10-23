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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var rvWeather: CarouselRecyclerView
    private val adapterWeather = WeatherAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_home_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWeather = view.findViewById(R.id.lyt_homeWeather_crv_weather)
        rvWeather.adapter = adapterWeather

        Log.d(TAG, "viewModel $viewModel")
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
}
