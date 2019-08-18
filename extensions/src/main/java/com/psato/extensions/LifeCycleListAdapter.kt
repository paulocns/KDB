package com.psato.extensions

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * An adapter that call the correct methods on a LifeCycleViewHolder
 * to ensure its life cycle is correct.
 *
 * @param T the type of your items.
 * @param VH the type of your view holder that must extends LifeCycleViewHolder.
 */
abstract class LifeCycleListAdapter<T, VH : LifeCycleViewHolder> : ListAdapter<T, VH> {
    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)
    constructor(config: AsyncDifferConfig<T>) : super(config)


    /**
     * @deprecated this method should not be override , implement onBindViewHolderImpl instead.
     */
    @Deprecated("onBindViewHolder should not be override, implement onBindViewHolderImpl instead")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.notifyBindViewHolder()
        onBindViewHolderImpl(holder, position)
    }

    /**
     * @param holder the ViewHolder that will be bound.
     * @param position the position of the item that will be bound to the ViewHolder.
     */
    protected abstract fun onBindViewHolderImpl(holder: VH, position: Int)


    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.notifyUnbindViewHolder()
    }
}