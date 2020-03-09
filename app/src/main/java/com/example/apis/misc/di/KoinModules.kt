package com.example.apis.misc.di

import com.example.apis.misc.sharedpreferences.ApisPreferencesImpl
import com.example.apis.misc.sharedpreferences.ApisPreferences
import org.koin.dsl.module

object KoinModules {

    val mainModule = module {
        factory<ApisPreferences> { ApisPreferencesImpl() }
    }
}