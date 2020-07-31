package com.example.apis.misc.webservice.weather.model.metaweather

data class MetaWeatherForecastParent(
    val title: String,
    val location_type: String,
    val woeid: Long,
    val latt_long: String
)