package com.psato.onewaybind

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Method to bind a Livedata with a function, this function will be called every time the
 * value inside the live data changes.
 *
 * @param T the type of your binding.
 * @param data A LiveData of any type.
 * @param function A function that receives one parameter with the same type as the LiveData.
 */
fun <T : Any> LifecycleOwner.bind(data: LiveData<T>, function: (id: T) -> Unit) {
    data.observe(this, Observer { value ->
        value?.let {
            function.invoke(value)
        }
    })
}