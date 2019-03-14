package com.psato.kdbcore.databind

import android.arch.lifecycle.*
import java.lang.ref.WeakReference


/**
 * Method to two way bind the  MutableLiveData with a two way binder\
 * When the value inside the data changes the ui is updated, and if the user changes
 * the value on the ui the data is also changed.
 *
 * @param T the type of your binding.
 * @param data A MutableLiveData of any type.
 * @param twoWayBinder a TwoWayBinder of the same type as your MutableLiveData.
 */
fun <T : Any> LifecycleOwner.twoWayBind(
    data: MutableLiveData<T>,
    twoWayBinder: TwoWayBinder<T>
) {
    data.observe(this, Observer { value ->
        value?.let {
            twoWayBinder.oneWayBind.invoke(it)
        }
    })
    twoWayBinder.twoWayBind(data, this)

}


/**
 * Binder class that makes sure nothing it's is listening to lifecycle events
 * and make sure to remove the binding when the lifecycle changes.
 *
 * @param T the type of your binding.
 */
abstract class TwoWayBinder<T> : LifecycleObserver {

    private lateinit var dataReference: WeakReference<MutableLiveData<T>?>

    private lateinit var lifeCycleOwnerReference: WeakReference<LifecycleOwner>

    /**
     *  A method that receives a parameter as the same type as the two way bing,
     *  basically is the method that should be called when the values of the LiveData changes.
     */
    abstract val oneWayBind: (it: T) -> Unit

    /**
     * This method receives a WeakReference with the MutableLiveData that will be passed
     * on the TwoWayBind method, in this method you should observe the field that you want to bind,
     * here you set/add your listener to the view and update the value inside the mutable live data.
     */
    abstract fun observeField(dataReference: WeakReference<MutableLiveData<T>?>)

    /**
     * this method should remove any listener created on the observeField method,
     * this is made to avoid memory leak, this is an abstract method to make sure
     * that the listener will be removed
     */
    abstract fun removeObserver()

    fun twoWayBind(data: MutableLiveData<T>, owner: LifecycleOwner) {
        dataReference = WeakReference(data)
        lifeCycleOwnerReference = WeakReference(owner)
        observeField(dataReference)
        owner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        removeObserver()
        val lifecycleOwner = lifeCycleOwnerReference.get()
        lifecycleOwner?.let {
            it.lifecycle.removeObserver(this)
        }
    }

}