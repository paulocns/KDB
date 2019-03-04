package com.psato.kdbcore.databind

import android.support.v7.widget.RecyclerView


abstract class LifeCycleAdapter<T, VH : LifeCycleViewHolder>(private val viewModel: AdapterViewModel<T>) : RecyclerView.Adapter<VH>() {

    override fun getItemCount() = viewModel.getItemsCount()

    override fun getItemViewType(position: Int): Int  = viewModel.getViewType(position)


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.notifyBindViewHolder()
        onBindViewHolderImpl(holder, position)
    }

    protected abstract fun onBindViewHolderImpl(holder: VH, position: Int)


    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.notifyUnbindViewHolder()
    }
}