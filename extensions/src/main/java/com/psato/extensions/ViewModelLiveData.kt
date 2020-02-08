package com.psato.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface LiveDataFactory {
    fun <T> ViewModel.liveData(value: T) = lazy {
        MutableLiveData(value) as LiveData<T>
    }

    fun <T> ViewModel.liveData() = lazy {
        MutableLiveData<T>() as LiveData<T>
    }

}

interface LiveDataSetter{
    operator fun <T> LiveData<T>.invoke(value: T) {
        require(this is MutableLiveData)
        val viewModelLiveData = this
        viewModelLiveData.value = value
    }
}