package com.example.apis.main.home.business

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apis.misc.livedata.ListHolder
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

private const val TAG = "HomeWeatherViewModel"
class HomeWeatherViewModel: ViewModel() {

    private val disposable = CompositeDisposable()

    private val weatherList = ListHolder<WeatherCarousel>()
    private val weatherLD = MutableLiveData<ListHolder<WeatherCarousel>>()

    private val weatherRepository = WeatherRepository()

    fun init() {
        Log.d(TAG, "HomeWeatherViewModel $this")
        initWeather()
    }

    private fun initWeather() {
        disposable.add(
            weatherRepository.fetchOptions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "weather options ${it.size}")

                        weatherList.resetList(it)
                        weatherLD.postValue(weatherList)
                        loadWeather()
                    },
                    { error ->
                        error.printStackTrace()
                    }
                )
        )
    }

    private fun loadWeather() {
        disposable.add(
            Completable.create { emitter ->
                Log.d(TAG, "WEATHER init foreach for calls")
                weatherRepository.getEndPointsFor5DaysWeather(36.96, -122.02).forEach {
                    Log.d(TAG, "fetching data")
                    fetchWeatherData(it)
                }

                Log.d(TAG, "WEATHER end foreach for calls")
                emitter.onComplete()
            }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun fetchWeatherData(item: Observable<WeatherCarousel>) {
        disposable.add(
            item
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { wsItem ->
                        Log.d(TAG, "WEATHER data fetched ${wsItem.type}")

                        weatherList.clearChangedItems()
                        val isUpdated = weatherList.updateItem(
                            { items ->
                                items.indexOfFirst {
                                    it.type == wsItem.type
                                }
                            },
                            { it.resetData(wsItem.data) }
                        )

                        if (!isUpdated) return@subscribe

                        weatherLD.postValue(weatherList)
                    },
                    { error ->
                        error.printStackTrace()
                    }
                )
        )
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared $this")

        super.onCleared()

        disposable.dispose()
        weatherRepository.closeConnections()
    }

    fun getWeather(): LiveData<ListHolder<WeatherCarousel>> = weatherLD
}