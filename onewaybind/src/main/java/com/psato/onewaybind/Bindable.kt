package com.psato.onewaybind

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

interface Bindable {

    val lifeCycleOwner : LifecycleOwner
    get() {
        return when(this){
            is Fragment -> this.viewLifecycleOwner
            is AppCompatActivity -> this
            else -> throw IllegalStateException("lifeCycleOwner could not be found in Bindable interface, please override it")
        }
    }

    fun <T> LiveData<T>.bind(function: (data: T) -> Unit){
        observe(lifeCycleOwner, Observer { value ->
            value?.let {
                function(value)
            }
        })
    }
}