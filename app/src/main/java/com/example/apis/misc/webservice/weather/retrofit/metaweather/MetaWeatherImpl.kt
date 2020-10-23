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
import com.example.apis.misc.webservice.weather.model.metaweather.MetaWeatherForecastConsolidated
import com.example.apis.misc.webservice.weather.model.metaweather.MetaWeatherLocation
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MetaWeatherImpl"
class MetaWeatherImpl(val retrofit: MetaWeatherRetrofit): WeatherAPI {

    override fun fetch5DaysWeather(latitude: Double, longitude: Double): Observable<List<WeatherData>> {
        return getLocation(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .flatMap{ location ->
                    //SystemClock.sleep(((Math.random() * 3) * 1000 * 5).toLong())
                    getWeatherFor5Days(location)
                }
    }

    override fun fetchDailyWeather(latitude: Double, longitude: Double): Observable<WeatherData> {
        return fetchWeatherByDate(latitude, longitude, Date())
    }

    override fun fetchWeatherByDate(latitude: Double, longitude: Double, date: Date): Observable<WeatherData> {
        return getLocation(latitude, longitude)
            .subscribeOn(Schedulers.io())
            .flatMap{ location ->
                SystemClock.sleep(((Math.random() * 3) * 1000 * 5).toLong())
                getWeatherByDate(location, date)
            }
    }

    override fun reload(weatherData: WeatherData): Observable<WeatherData> {
        TODO("Not yet implemented")
    }

    private fun getWeatherByDate(location: MetaWeatherLocation, date: Date): Observable<WeatherData> {
        return Observable.create { emitter ->
            Log.d(TAG, "getWeatherByDate [${location.title} | ${location.woeid}] $date")

            val calendar = fetchCalendar(date)
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val response = retrofit.getWeatherByDay(location.woeid, year, month, day).execute()

            if (!response.isSuccessful) {
                emitter.onError(WeatherException(WeatherExceptionType.DETAILS, response.message()))
                return@create
            }

            response.body().let { forecast ->

                if (forecast == null) {
                    emitter.onError(WeatherException(WeatherExceptionType.DETAILS_NOT_FOUND, "Forecast not found for ${location.title}"))
                    return@create
                }

                val coordinates = location.latt_long.split(",")
                val dailyResults = forecast.map {
                    createWeatherData(location.title, coordinates, it)
                }

                if (dailyResults.isNullOrEmpty()) {
                    emitter.onError(WeatherException(WeatherExceptionType.DETAILS_NOT_FOUND, "Forecast not found for ${location.title} on $date"))
                    return@create
                }

                emitter.onNext(dailyResults[0])
            }
        }
    }

    private fun fetchCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar
    }

    private fun getWeatherFor5Days(location: MetaWeatherLocation): Observable<List<WeatherData>> {
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

    private fun processForecastResult(forecast: MetaWeatherForecast, limit: Int = 5): List<WeatherData> {

        val result = mutableListOf<WeatherData>()

        forecast.consolidated_weather.forEachIndexed { index, it ->
            if (index == limit) return@forEachIndexed

            val latLng = forecast.latt_long.split(",")
            result.add(createWeatherData(forecast.title, latLng, it))
        }

        return result
    }

    private fun createWeatherData(location: String, coordinates: List<String>, forecastConsolidated: MetaWeatherForecastConsolidated): WeatherData {
        return WeatherData(
            location,
            coordinates[0].toDouble(),
            coordinates[1].toDouble(),
            getWeatherState(forecastConsolidated.weather_state_abbr),
            SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(forecastConsolidated.applicable_date)!!,
            forecastConsolidated.the_temp,
            forecastConsolidated.min_temp,
            forecastConsolidated.max_temp,
            getWindDirection(forecastConsolidated.wind_direction_compass),
            forecastConsolidated.wind_speed,
            forecastConsolidated.air_pressure,
            forecastConsolidated.humidity,
            forecastConsolidated.visibility,
            forecastConsolidated.predictability
        )
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