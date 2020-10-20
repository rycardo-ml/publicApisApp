package com.example.apis.misc.livedata

class ListHolder<T>(val list: MutableList<T> = mutableListOf()) {
    var indexChanged: Int = -1
    var type: ListHolderType = ListHolderType.FULL

    fun resetList(newList: List<T>) {
        list.clear()
        list.addAll(newList)

        type = ListHolderType.FULL
    }

    fun updateItem(filter: (T) -> Boolean, update: (T) -> Unit): Boolean {
        val indexOf = list.indexOfFirst { filter.invoke(it) }

        if (indexOf == -1) return false

        update.invoke(list[indexOf])

        indexChanged = indexOf
        type = ListHolderType.UPDATE

        return true
    }
}