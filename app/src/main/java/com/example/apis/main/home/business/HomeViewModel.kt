package com.example.apis.main.home.business

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apis.misc.livedata.ListHolder
import com.example.apis.misc.models.Category
import com.example.apis.misc.models.EnumCategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

private const val TAG = "HomeViewModel"
class HomeViewModel: ViewModel() {

    private val disposable = CompositeDisposable()

    private val categoryList = ListHolder<Category>()
    private val categoryLD = MutableLiveData<ListHolder<Category>>()
    private val categorySelectedLD = MutableLiveData<EnumCategory>()

    private val categoryRepository = CategoryRepository()


    fun init() {
        Log.d(TAG, "HomeViewModel $this")
        initCategories()
    }

    fun updateSelectedCategory(selectedIndex: Int) {

        categoryList.clearChangedItems()

        categoryList.updateItem(
            { items -> items.indexOfFirst { it.selected } },
            { it.selected = false }
        )

        categoryList.updateItem(
            { selectedIndex },
            { it.selected = true }
        )

        categorySelectedLD.postValue(categoryList.get(selectedIndex).type)
        categoryLD.postValue(categoryList)
    }

    private fun initCategories() {
        disposable.add(
            categoryRepository.fetchOptions()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        categoryList.resetList(it)
                        categoryLD.postValue(categoryList)
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
    }

    fun getCategory(): LiveData<ListHolder<Category>> = categoryLD
    fun getCategorySelected(): LiveData<EnumCategory> = categorySelectedLD
}
