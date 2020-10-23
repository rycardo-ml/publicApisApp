package com.example.apis.main.home.business

import com.example.apis.misc.ui.CarouselStatus
import com.example.apis.misc.webservice.weather.WeatherType
import com.example.apis.misc.webservice.weather.model.WeatherData

data class WeatherCarousel(val type: WeatherType) {
    var status = CarouselStatus.LOADING
    var weather: WeatherData? = null
}
