package com.example.apis.preferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apis.R

class PreferencesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment()).commit()
    }
}