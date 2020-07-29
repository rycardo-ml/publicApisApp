package com.example.apis.misc.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.apis.misc.webservice.WebServiceType
import com.example.apis.misc.webservice.getWebServiceTypeById
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.ext.scope

private const val TAG = "ApisPreferencesImpl"
class ApisPreferencesImpl: ApisPreferences, KoinComponent {

    private val context: Context by inject()
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        Log.d(TAG, "init ${context}")
    }

    override fun getWebServiceType(): WebServiceType {
        Log.d(TAG, "getWebServiceType $context")

        val networkTypeId = preferences.getInt("network_type", WebServiceType.RETROFIT.id)
        return getWebServiceTypeById(networkTypeId)
    }
}