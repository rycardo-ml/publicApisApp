package com.example.apis.misc.webservice.weather.model.metaweather

data class MetaWeatherConsolidated(
    val id: Long,
    val weather_state_name: String,
    val weather_state_abbr: String,
    val wind_direction_compass: String,
    val created: String, //2020-07-29T12:18:04.872273Z
    val applicable_date: String, //2020-07-29
    val min_temp: Float,
    val max_temp: Float,
    val the_temp: Float,
    val wind_speed: Float,
    val wind_direction: Float,
    val air_pressure: Float,
    val humidity: Int,
    val visibility: Float,
    val predictability: Int
)