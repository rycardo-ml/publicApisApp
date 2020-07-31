package com.example.apis.misc.webservice.weather

import java.lang.Exception

class WeatherException(val type: WeatherExceptionType, message: String): Exception(message)

enum class WeatherExceptionType {
    LOCATION,
    LOCATION_NOT_FOUND,
    DETAILS,
    DETAILS_NOT_FOUND
}
