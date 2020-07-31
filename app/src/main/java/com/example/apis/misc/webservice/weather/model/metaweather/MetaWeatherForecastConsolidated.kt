package com.example.apis.misc.webservice.weather.model.metaweather

data class MetaWeatherForecastConsolidated(
    val id: Long,

    val weather_state_name: String,
    val weather_state_abbr: String,

    val min_temp: Float,
    val max_temp: Float,
    val the_temp: Float,

    val wind_speed: Float,
    val wind_direction: Float,
    val wind_direction_compass: String,

    val created: String, //2020-07-31T15:18:02.175959Z
    val applicable_date: String, //2020-07-29

    val air_pressure: Float,
    val humidity: Int,
    val visibility: Float,
    val predictability: Int
)