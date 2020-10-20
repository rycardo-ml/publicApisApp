package com.example.apis.misc.di

import com.example.apis.misc.sharedpreferences.ApisPreferencesImpl
import com.example.apis.misc.sharedpreferences.ApisPreferences
import com.example.apis.misc.webservice.weather.WeatherAPI
import com.example.apis.misc.webservice.weather.WeatherType
import com.example.apis.misc.webservice.weather.retrofit.RetrofitWeather
import com.example.apis.misc.webservice.weather.retrofit.metaweather.MetaWeatherImpl
import com.example.apis.misc.webservice.weather.retrofit.metaweather.RetrofitMetaWeatherBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module

object KoinModules {

    val mainModule = module {
        factory<ApisPreferences> { ApisPreferencesImpl() }
    }

    val retrofitWeatherModules = module {
        scope(named(WeatherType.META_WEATHER.scope)) {
            factory { RetrofitMetaWeatherBuilder.provideOkHttpClient() }
            factory { RetrofitMetaWeatherBuilder.provideApi(get()) }
            scoped { RetrofitMetaWeatherBuilder.provideRetrofit(get()) }
            scoped<WeatherAPI> { MetaWeatherImpl(get()) }
        }

        scope(named(WeatherType.OPEN_WEATHER.scope)) {
            factory { RetrofitMetaWeatherBuilder.provideOkHttpClient() }
            factory { RetrofitMetaWeatherBuilder.provideApi(get()) }
            scoped { RetrofitMetaWeatherBuilder.provideRetrofit(get()) }
            scoped<WeatherAPI> { MetaWeatherImpl(get()) }
        }

        scope(named(WeatherType.OTHER_WEATHER.scope)) {
            factory { RetrofitMetaWeatherBuilder.provideOkHttpClient() }
            factory { RetrofitMetaWeatherBuilder.provideApi(get()) }
            scoped { RetrofitMetaWeatherBuilder.provideRetrofit(get()) }
            scoped<WeatherAPI> { MetaWeatherImpl(get()) }
        }

        factory { (type: WeatherType) ->
            RetrofitWeather(type)
        }
    }
}