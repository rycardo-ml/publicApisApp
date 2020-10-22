package com.example.apis.main.home.business

import com.example.apis.misc.models.Category
import com.example.apis.misc.models.EnumCategory
import io.reactivex.Observable

class CategoryRepository {

    fun fetchOptions(): Observable<List<Category>> {
        return Observable.create {
            it.onNext(
                listOf(
                    Category(EnumCategory.WEATHER),
                    Category(EnumCategory.MOVIES),
                    Category(EnumCategory.MUSIC)

                    ,
                    Category(EnumCategory.TMP1),
                    Category(EnumCategory.TMP2),
                    Category(EnumCategory.TMP3),
                    Category(EnumCategory.TMP4),
                    Category(EnumCategory.TMP5),
                    Category(EnumCategory.TMP6)
                )
            )

            it.onComplete()
        }
    }
}