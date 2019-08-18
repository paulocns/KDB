package com.psato.extensions

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

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
    open fun notifyBindViewHolder() {
        val lifecycleRegistry = lifecycle as LifecycleRegistry
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    /**
     * Function to set the lifecycle as onDestroy state.
     */
    open fun notifyUnbindViewHolder() {
        val lifecycleRegistry = lifecycle as LifecycleRegistry
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

}