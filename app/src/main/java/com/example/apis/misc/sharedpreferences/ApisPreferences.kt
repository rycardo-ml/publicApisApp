package com.example.apis.misc.sharedpreferences

import com.example.apis.misc.webservice.WebServiceType
import org.koin.core.KoinComponent

interface ApisPreferences: KoinComponent {

    fun setWebServiceType(networkType: WebServiceType)
    fun getWebServiceType(): WebServiceType

}