package com.psato.kdbcore.databind

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

fun <T : Any> LifecycleOwner.bind(
    data: LiveData<T>,
    function: (id: T) -> Unit,
    transformer: (value: T) -> T = { it }
) {
    data.observe(this, Observer { value ->
        value?.let {
            val value = transformer(it)
            function.invoke(value)
        }
    })
}