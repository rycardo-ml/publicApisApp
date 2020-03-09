package com.example.apis.misc.webservice

enum class WebServiceType(val id: Int) {
    RETROFIT(1),
    VOLEY(2),
}

fun getWebServiceTypeById(id: Int): WebServiceType {
    return WebServiceType.values().first { value -> value.id == id }
}