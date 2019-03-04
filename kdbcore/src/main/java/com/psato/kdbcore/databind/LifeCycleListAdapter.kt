package com.psato.kdbcore.databind

import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil

abstract class LifeCycleListAdapter<T, VH : LifeCycleViewHolder> : ListAdapter<T, VH> {
    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)
    constructor(config: AsyncDifferConfig<T>) : super(config)


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.notifyBindViewHolder()
        onBindViewHolderImpl(holder, position)
    }

    protected abstract fun onBindViewHolderImpl(holder: VH, position: Int);


    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.notifyUnbindViewHolder()
    }
}