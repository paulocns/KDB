package com.psato.kdbcore.databind

import android.arch.lifecycle.*
import java.lang.ref.WeakReference

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


abstract class TwoWayBinder<T> : LifecycleObserver {

    private lateinit var dataReference: WeakReference<MutableLiveData<T>?>

    private lateinit var lifeCycleOwnerReference: WeakReference<LifecycleOwner>

    abstract val oneWayBind: (it: T) -> Unit

    abstract fun observeField(dataReference: WeakReference<MutableLiveData<T>?>)

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