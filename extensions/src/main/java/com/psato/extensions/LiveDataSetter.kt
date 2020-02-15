package com.psato.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


/**
 *  Interface that gives the invoke method to a LiveData
 */
interface LiveDataSetter {
    /**
     *  This method require that this LiveData is a MutableLiveData,
     *  cast it to MutableLiveData and set it's value
     *  @param T type of live data
     *  @param value value to be set on the live data
     */
    operator fun <T> LiveData<T>.invoke(value: T) {
        require(this is MutableLiveData)
        val viewModelLiveData = this
        viewModelLiveData.value = value
    }
}