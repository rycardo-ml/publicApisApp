package com.example.apis.main.home.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.apis.R
import com.example.apis.main.home.business.HomeViewModel
import com.example.apis.main.home.ui.adapter.WeatherAdapter
import com.example.apis.misc.livedata.ListHolderType
import com.example.apis.misc.ui.CarouselRecyclerView
import com.example.apis.preferences.PreferencesActivity
import java.lang.UnsupportedOperationException


private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var rvWeather: CarouselRecyclerView
    private val adapterWeather = WeatherAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvPreferences = view.findViewById<TextView>(R.id.frg_mainhome_lb_preferences)
        tvPreferences.setOnClickListener { openPreferences() }

        rvWeather = view.findViewById(R.id.frg_mainHome_crv_weather)
        rvWeather.initialize(adapterWeather)
        //rvWeather.setViewsToChangeColor(listOf(R.id.row_weather_iv, R.id.row_weather_tv))

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

    private fun openPreferences() {
        startActivity(Intent(activity, PreferencesActivity::class.java))
    }

    private fun registerObservers() {
        viewModel.getWeather().observe(viewLifecycleOwner, Observer {

            if (it.type == ListHolderType.FULL) {
                adapterWeather.updateItems(it.list)
                return@Observer
            }

            if (it.type == ListHolderType.UPDATE && it.indexChanged != -1) {
                Log.d(TAG, "update one item")
                adapterWeather.notifyItemChanged(it.indexChanged)
                return@Observer
            }

            throw UnsupportedOperationException("List holder type not handled ${it.type}")
        })
    }

    private fun unregisterObservers() {
        viewModel.getWeather().removeObservers(viewLifecycleOwner)
    }
}
