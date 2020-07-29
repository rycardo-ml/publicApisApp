package com.example.apis.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.apis.R
import com.example.apis.misc.sharedpreferences.ApisPreferences
import com.example.apis.misc.webservice.weather.WeatherAPI
import com.example.apis.misc.webservice.weather.WeatherType
import com.example.apis.misc.webservice.weather.retrofit.RetrofitWeather
import com.example.apis.preferences.PreferencesActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.experimental.property.inject

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {


    val metaWeatherAPI: RetrofitWeather by inject { parametersOf(WeatherType.META_WEATHER) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvPreferences = view.findViewById<TextView>(R.id.frg_mainhome_lb_preferences)
        tvPreferences.setOnClickListener { openPreferences() }

        testConnection()
    }

    private fun openPreferences() {
        startActivity(Intent(activity, PreferencesActivity::class.java))
    }
    
    private fun testConnection() {
        Log.d(TAG, "eita vamos ver $metaWeatherAPI")
//        val request = RetrofitMetaWeatherBuilder.buildService(MetaWeatherRetrofit::class.java)
//        val call = request.getLocationByLatLng("36.96,-122.02")
//
//        call.enqueue(object : Callback<List<MetaWeatherLocation>>{
//            override fun onResponse(call: Call<List<MetaWeatherLocation>>, response: Response<List<MetaWeatherLocation>>) {
//                if (response.isSuccessful){
//                    Log.d(TAG, "finalizou e retornou [${response.body()!!.size}]")
//                }
//            }
//            override fun onFailure(call: Call<List<MetaWeatherLocation>>, t: Throwable) {
//                Log.e(TAG, "ops we have a problem", t)
//            }
//        })
    }
}
