package com.example.apis.misc.livedata

class ListHolder<T>(val list: MutableList<T> = mutableListOf()) {
    var changedItems = mutableListOf<ChangedItem>()
    var type: ListHolderType = ListHolderType.FULL

    fun resetList(newList: List<T>) {
        list.clear()
        list.addAll(newList)

        type = ListHolderType.FULL
    }

    fun updateItem(indexOfItem: (List<T>) -> Int, update: (T) -> Unit): Boolean {
        val indexOf = indexOfItem.invoke(list)

        if (indexOf == -1) return false

        update.invoke(list[indexOf])

        changedItems.add(ChangedItem(indexOf, ChangedItemType.UPDATE))
        type = ListHolderType.PARTIAL

        return true
    }

    fun clearChangedItems() {
        changedItems.clear()
    }

    fun get(index: Int): T = list[index]
}