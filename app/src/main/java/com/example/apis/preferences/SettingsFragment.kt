package com.example.apis.preferences

import android.os.Bundle
import android.preference.PreferenceFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.example.apis.R
import com.example.apis.misc.sharedpreferences.ApisPreferences
import org.koin.android.ext.android.inject

private const val TAG = "PreferencesFragment"
class SettingsFragment: PreferenceFragmentCompat() {

    val preferences by inject<ApisPreferences>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        Log.d(TAG, "onCreateView $preferences")
    }

}
