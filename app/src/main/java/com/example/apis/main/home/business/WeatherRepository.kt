package com.example.apis.main.home.business

import com.example.apis.misc.di.ApiComponent
import com.example.apis.misc.di.inject
import com.example.apis.misc.webservice.weather.WeatherType
import com.example.apis.misc.webservice.weather.retrofit.RetrofitWeather
import io.reactivex.Observable
import org.koin.core.parameter.parametersOf

class WeatherRepository {

    private val items = mutableMapOf<WeatherType, WeatherWrapper>()

    fun fetchOptions(): Observable<List<WeatherCarousel>> {
        return Observable.create {
            items.clear()

            items[WeatherType.META_WEATHER] = WeatherWrapper(WeatherType.META_WEATHER)
            items[WeatherType.OPEN_WEATHER] = WeatherWrapper(WeatherType.OPEN_WEATHER)
            items[WeatherType.OTHER_WEATHER] = WeatherWrapper(WeatherType.OTHER_WEATHER)

            it.onNext(getCarouselOptions())
        }
    }

    fun getEndPointsFor5DaysWeather(latitude: Double, longitude: Double):  List<Observable<WeatherCarousel>> {
        return items.values.map main@ { wrapper ->
            wrapper.retrofit.api.get5DaysWeather(latitude, longitude)
                .map inner@ {
                    wrapper.item.resetData(it)
                    return@inner wrapper.item
                }
        }
    }

    private fun getCarouselOptions(): List<WeatherCarousel> {
        return items.values.map { it.item }
    }

    fun closeConnections() {
        items.values.map { it.retrofit.close() }
    }

    inner class WeatherWrapper(val type: WeatherType): ApiComponent {
        val retrofit: RetrofitWeather by inject { parametersOf(type) }
        val item = WeatherCarousel(type)

    }
}