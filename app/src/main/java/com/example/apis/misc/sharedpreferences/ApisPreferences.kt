package com.example.apis.misc.sharedpreferences

import com.example.apis.misc.webservice.WebServiceType

interface ApisPreferences {
    fun getWebServiceType(): WebServiceType
}