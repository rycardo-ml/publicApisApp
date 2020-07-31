package com.example.apis.misc.webservice.weather.model.metaweather

data class MetaWeatherForecastSource(
    val title: String,
    val slug: String,
    val url: String,
    val crawl_rate: Int
)