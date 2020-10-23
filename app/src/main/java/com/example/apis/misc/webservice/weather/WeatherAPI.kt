package com.example.apis.misc.webservice.weather

import com.example.apis.misc.webservice.weather.model.WeatherData
import io.reactivex.Observable
import java.util.*

interface WeatherAPI {
    fun fetch5DaysWeather(latitude: Double, longitude: Double): Observable<List<WeatherData>>
    fun fetchDailyWeather(latitude: Double, longitude: Double): Observable<WeatherData>
    fun fetchWeatherByDate(latitude: Double, longitude: Double, date: Date): Observable<WeatherData>
    fun reload(weatherData: WeatherData): Observable<WeatherData>
}