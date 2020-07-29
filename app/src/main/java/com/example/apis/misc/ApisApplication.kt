package com.example.apis.misc

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.example.apis.misc.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

private const val TAG = "ApisApplication"
class ApisApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate")

        initKoin()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initKoin() {
        startKoin {
            Log.d(TAG, "startKoin ${this@ApisApplication}")

            androidLogger()
            androidContext(this@ApisApplication)
            initModules(this)
        }
    }

    private fun initModules(koinApplication: KoinApplication) {
        Log.d(TAG, "initModules application[${this@ApisApplication}]" )

        koinApplication.modules(listOf(
            KoinModules.mainModule,
            KoinModules.retrofitWeatherModules
        ))
    }
}