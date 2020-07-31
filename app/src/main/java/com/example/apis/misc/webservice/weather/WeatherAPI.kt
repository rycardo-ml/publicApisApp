package com.example.apis.misc.webservice.weather

import com.example.apis.misc.webservice.weather.model.WeatherData
import io.reactivex.Observable
import java.util.*

interface WeatherAPI {
    fun get5DaysWeather(latitude: Double, longitude: Double): Observable<List<WeatherData>>
    fun getDailyWeather(latitude: Double, longitude: Double): Observable<WeatherData>
    fun getWeatherByDate(latitude: Double, longitude: Double, date: Date): Observable<WeatherData>
    fun reload(weatherData: WeatherData): Observable<WeatherData>
}