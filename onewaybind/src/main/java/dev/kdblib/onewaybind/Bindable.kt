package dev.kdblib.onewaybind

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**
 *  Interface created to expose the bind function, it will automatically select the correct LifecycleOwner
 *  if inside an Activity or Fragment and add an observer to a LiveData
 */
interface Bindable {


    /**
     *  lifeCycleOwner is the LifeCycleOwner that will be used to bind to a LiveData
     *  If inside an Activity or Fragment it will automatically select the correct one
     */
    val lifeCycleOwner: LifecycleOwner
        get() {
            return when (this) {
                is Fragment -> this.viewLifecycleOwner
                is AppCompatActivity -> this
                else -> throw IllegalStateException("lifeCycleOwner could not be found in Bindable interface, please override it")
            }
        }

    /**
     *  The Bind extension function it creates an observer for the LiveData and call the block of code passed as the parameter
     *
     *  @param T the type of your binding.
     *  @param function block of code that will be executed on update
     */
    fun <T> LiveData<T>.bind(function: (data: T) -> Unit) {
        observe(lifeCycleOwner, { value ->
            value?.let {
                function(value)
            }
        })
    }

    /**
     *  The Bind extension function it creates an observer for the LiveData and call the block of code passed as the parameter
     *
     *  @param T the type of your binding.
     *  @param function block of code that will be executed on update
     */
    fun <T> LiveData<T>.bindNullable(function: (data: T?) -> Unit) {
        observe(lifeCycleOwner, { value ->
                function(value)
        })
    }
}