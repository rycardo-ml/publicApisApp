package com.example.apis.misc.webservice.weather

import java.util.*

interface WeatherAPI {
    fun get5DaysWeather(latitude: Double, longitude: Double)
    fun getDailyWeather(latitude: Double, longitude: Double)
    fun getWeatherByDate(latitude: Double, longitude: Double, date: Date)
}