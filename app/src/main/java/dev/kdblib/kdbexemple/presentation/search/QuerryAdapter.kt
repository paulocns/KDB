package dev.kdblib.kdbexemple.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.kdblib.kdbexemple.R
import dev.kdblib.extensions.AdapterViewModel
import dev.kdblib.extensions.LifeCycleAdapter
import dev.kdblib.extensions.LifeCycleViewHolder
import dev.kdblib.kdbexemple.infrastructure.bindView


class QuerryAdapter(private val viewModel: AdapterViewModel<ShowResponseItem>) :
    LifeCycleAdapter<ShowResponseItem, QuerryAdapter.SearchViewHolder>(viewModel) {

    override fun onBindViewHolderImpl(holder: SearchViewHolder, position: Int) {
        holder.bindToView(viewModel.getItemAtPosition(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false)
        return SearchViewHolder(v)
    }

    class SearchViewHolder(itemView: View) : LifeCycleViewHolder(itemView) {
        private val showName: TextView by bindView(R.id.show_name)
        fun bindToView(item: ShowResponseItem) {
            item.showName.bind(showName::setText)
        }
    }

}