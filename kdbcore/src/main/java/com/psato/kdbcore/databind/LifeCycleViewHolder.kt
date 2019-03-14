package com.psato.kdbcore.databind

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class LifeCycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    LifecycleOwner {

    private var _lifeCycle: Lifecycle? = null

    /**
     * @return the Lifecycle for this ViewHolder.
     */
    override fun getLifecycle(): Lifecycle {
        if (_lifeCycle == null) {
            _lifeCycle = LifecycleRegistry(this)
        }
        return _lifeCycle as Lifecycle
    }

    /**
     * Function to set the lifecycle as onResume state.
     */
    fun notifyBindViewHolder() {
        val lifecycleRegistry = lifecycle as LifecycleRegistry
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    /**
     * Function to set the lifecycle as onDestroy state.
     */
    fun notifyUnbindViewHolder() {
        val lifecycleRegistry = lifecycle as LifecycleRegistry
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

}