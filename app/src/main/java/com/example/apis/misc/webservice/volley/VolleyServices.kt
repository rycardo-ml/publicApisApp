package com.example.apis.misc.webservice.volley

import android.util.Log
import com.example.apis.misc.webservice.WebServices

private const val TAG = "VolleyServices"
class VolleyServices: WebServices {

    override fun fetchUsers() {
        Log.d(TAG, "fetchUsers")

        val URL = "https://api.github.com/users"



    }



}
