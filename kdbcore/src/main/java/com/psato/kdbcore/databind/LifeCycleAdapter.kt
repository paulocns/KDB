package com.psato.kdbcore.databind

import android.support.v7.widget.RecyclerView

/**
 * An adapter that call the correct methods on a LifeCycleViewHolder
 * to ensure its life cycle is correct.
 *
 * @param T the type of your items.
 * @param VH the type of your view holder that must extends LifeCycleViewHolder.
 * @property viewModel an AdapterViewModel of type T that will control the dataset for this adapter.
 */
abstract class LifeCycleAdapter<T, VH : LifeCycleViewHolder>(private val viewModel: AdapterViewModel<T>) : RecyclerView.Adapter<VH>() {

    override fun getItemCount() = viewModel.getItemsCount()

    override fun getItemViewType(position: Int): Int  = viewModel.getViewType(position)


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