package com.example.apis.misc.webservice.weather.model

import java.util.*

data class WeatherData(
    val location: String,
    val latitude: Double,
    val longitude: Double,

    val weatherState: WeatherState,

    val date: Date,

    val temperature: Float,
    val temperatureMin: Float,
    val temperatureMax: Float,

    val windDirection: WindDirection,
    val windSpeed: Float,

    val airPressure: Float,
    val humidity: Int,
    val visibility: Float,
    val predictability: Int
) {
    val uuid: UUID = UUID.randomUUID()
}
