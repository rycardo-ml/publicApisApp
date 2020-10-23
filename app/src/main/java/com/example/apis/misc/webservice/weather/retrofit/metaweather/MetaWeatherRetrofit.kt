package com.example.apis.misc.webservice.weather.retrofit.metaweather

import com.example.apis.misc.webservice.weather.model.metaweather.MetaWeatherForecast
import com.example.apis.misc.webservice.weather.model.metaweather.MetaWeatherForecastConsolidated
import com.example.apis.misc.webservice.weather.model.metaweather.MetaWeatherLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * https://www.metaweather.com/api/
 */
interface MetaWeatherRetrofit {

    // https://www.metaweather.com/api/location/search/?lattlong=36.96,-122.02
    @GET("/api/location/search/")
    fun getLocationByLatLng(@Query("lattlong", encoded = true) latLng: String): Call<List<MetaWeatherLocation>>

    // https://www.metaweather.com/api/location/44418/
    @GET("/api/location/{woeid}")
    fun getWeather(@Path("woeid") woeid: Long): Call<MetaWeatherForecast>

    // https://www.metaweather.com/api/location/44418/2013/4/27/
    @GET("/api/location/{woeid}/{year}/{month}/{day}")
    fun getWeatherByDay(@Path("woeid") woeid: Long,
                        @Path("year") year: Int,
                        @Path("month") month: Int,
                        @Path("day") day: Int): Call<List<MetaWeatherForecastConsolidated>>
}
