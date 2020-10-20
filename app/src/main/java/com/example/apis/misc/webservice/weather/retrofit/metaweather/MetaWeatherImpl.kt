package com.example.apis.misc.webservice.weather.retrofit.metaweather

import android.os.SystemClock
import android.util.Log
import com.example.apis.misc.webservice.weather.WeatherAPI
import com.example.apis.misc.webservice.weather.WeatherException
import com.example.apis.misc.webservice.weather.WeatherExceptionType
import com.example.apis.misc.webservice.weather.model.WeatherData
import com.example.apis.misc.webservice.weather.model.WeatherState
import com.example.apis.misc.webservice.weather.model.WindDirection
import com.example.apis.misc.webservice.weather.model.metaweather.MetaWeatherForecast
import com.example.apis.misc.webservice.weather.model.metaweather.MetaWeatherLocation
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MetaWeatherImpl"
class MetaWeatherImpl(val retrofit: MetaWeatherRetrofit): WeatherAPI {

    override fun get5DaysWeather(latitude: Double, longitude: Double): Observable<List<WeatherData>> {
        return getLocation(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .flatMap{ location ->
                    //SystemClock.sleep(((Math.random() * 3) * 1000 * 5).toLong())
                    getWeather(location)
                }
    }

    override fun getDailyWeather(latitude: Double, longitude: Double): Observable<WeatherData> {
        TODO("Not yet implemented")
    }

    override fun getWeatherByDate(latitude: Double, longitude: Double, date: Date): Observable<WeatherData> {
        TODO("Not yet implemented")
    }

    override fun reload(weatherData: WeatherData): Observable<WeatherData> {
        TODO("Not yet implemented")
    }

    private fun getWeather(location: MetaWeatherLocation): Observable<List<WeatherData>> {
        return Observable.create { emitter ->
            Log.d(TAG, "getWeather [${location.title} | ${location.woeid}]")

            val response = retrofit.getWeather(location.woeid).execute()

            if (!response.isSuccessful) {
                emitter.onError(WeatherException(WeatherExceptionType.DETAILS, response.message()))
                return@create
            }

            response.body().let { forecast ->

                if (forecast == null) {
                    emitter.onError(WeatherException(WeatherExceptionType.DETAILS_NOT_FOUND, "Forecast not found for ${location.title}"))
                    return@create
                }

                emitter.onNext(processForecastResult(forecast))
            }
        }
    }

    private fun getLocation(latitude: Double, longitude: Double): Observable<MetaWeatherLocation> {
        return Observable.create { emitter ->
            Log.d(TAG, "getLocation [$latitude | $longitude]")

            val response = retrofit.getLocationByLatLng("$latitude,$longitude").execute()

            if (!response.isSuccessful) {
                emitter.onError(WeatherException(WeatherExceptionType.LOCATION, response.message()))
                return@create
            }

            response.body().let { locations ->
                if (locations.isNullOrEmpty()) {
                    emitter.onError(WeatherException(WeatherExceptionType.LOCATION_NOT_FOUND, "Place not found for this coordinates"))
                    return@create
                }

                val location = locations.sortedBy { it.distance }[0]
                emitter.onNext(location)
            }
        }
    }

    private fun processForecastResult(forecast: MetaWeatherForecast): List<WeatherData> {

        val result = mutableListOf<WeatherData>()

        forecast.consolidated_weather.forEachIndexed { index, it ->
            if (index == 5) return@forEachIndexed

            val latLng = forecast.latt_long.split(",")

            result.add(WeatherData(
                forecast.title,
                latLng[0].toDouble(),
                latLng[1].toDouble(),
                getWeatherState(it.weather_state_abbr),
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(it.applicable_date)!!,
                it.the_temp,
                it.min_temp,
                it.max_temp,
                getWindDirection(it.wind_direction_compass),
                it.wind_speed,
                it.air_pressure,
                it.humidity,
                it.visibility,
                it.predictability
            ))
        }

        return result
    }

    private fun getWindDirection(direction: String): WindDirection {
        WindDirection.values().forEach {
            if (it.name != (direction.toUpperCase(Locale.getDefault()))) return@forEach
            return it
        }

        return WindDirection.UNDEFINED
    }

    private fun getWeatherState(state: String): WeatherState {
        return when (state) {
            "sn" -> WeatherState.SNOW
            "sl" -> WeatherState.SLEET
            "h" ->  WeatherState.HAIL
            "t" ->  WeatherState.THUNDERSTORM
            "hr" -> WeatherState.HEAVY_RAIN
            "lr" -> WeatherState.LIGHT_RAIN
            "s" ->  WeatherState.SHOWERS
            "hc" -> WeatherState.HEAVY_CLOUD
            "lc" -> WeatherState.LIGHT_CLOUD
            "c" ->  WeatherState.CLEAR
            else -> WeatherState.UNDEFINED
        }
    }
}