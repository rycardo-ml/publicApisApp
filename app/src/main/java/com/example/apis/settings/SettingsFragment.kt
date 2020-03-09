package com.example.apis.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apis.R
import com.example.apis.misc.sharedpreferences.ApisPreferences
import org.koin.android.ext.android.inject

private const val TAG = "SettingsFragment"
class SettingsFragment: Fragment() {

    val preferences by inject<ApisPreferences>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d(TAG, "onCreateView $preferences")

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}
