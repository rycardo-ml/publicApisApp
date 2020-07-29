package com.example.apis.misc.webservice.weather.retrofit

import com.example.apis.misc.webservice.weather.WeatherAPI
import com.example.apis.misc.webservice.weather.WeatherType
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class RetrofitWeather(type: WeatherType): KoinComponent {
    private val TAG = "RetrofitWeather"

    private val scope: Scope = getKoin().createScope("${type.scope}_id", named(type.scope))
    val api by scope.inject<WeatherAPI>()



    fun close() {
        scope.close()
    }
}