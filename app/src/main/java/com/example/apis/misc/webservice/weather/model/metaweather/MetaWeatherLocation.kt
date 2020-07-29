package com.example.apis.misc.webservice.weather.model.metaweather

data class MetaWeatherLocation(
    val title: String,
    val location_type: String,
    val latt_long: String, //"51.506321,-0.12714"
    val woeid: Long,
    val distance: Long //meters
)