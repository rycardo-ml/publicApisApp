package com.example.apis.misc.webservice.weather.model.metaweather

import java.util.*

data class MetaWeatherForecast(
    val woeid: Long,
    val title: String,
    val location_type: String,
    val latt_long: String,

    val sun_rise: Date,
    val sun_set: Date,
    val time: Date,
    val timezone_name: String,
    val timezone: String,

    val consolidated_weather: List<MetaWeatherForecastConsolidated>,
    val parent: MetaWeatherForecastParent,
    val sources: List<MetaWeatherForecastSource>
)
