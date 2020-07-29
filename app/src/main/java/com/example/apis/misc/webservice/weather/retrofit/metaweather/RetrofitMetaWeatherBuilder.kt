package com.example.apis.misc.webservice.weather.retrofit.metaweather

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitMetaWeatherBuilder {
    private const val TAG = "RMWBuilder"

    fun provideOkHttpClient(): OkHttpClient {
        Log.d(TAG, "provideOkHttpClient")
        return OkHttpClient.Builder().build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        Log.d(TAG, "provideRetrofit")

        return Retrofit.Builder()
            .baseUrl("https://www.metaweather.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun provideApi(retrofit: Retrofit): MetaWeatherRetrofit = retrofit.create(
        MetaWeatherRetrofit::class.java)
}