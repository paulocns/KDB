package com.psato.kdbcore.databind

interface AdapterViewModel<T> {
    fun getItemsCount(): Int
    fun getItemAtPosition(pos: Int): T
    fun getViewType(position: Int): Int {
        return 0
    }

}