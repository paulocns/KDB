package com.psato.kdbexemple.presentation.search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psato.kdbexemple.infrastructure.onClick
import com.psato.kdbexemple.infrastructure.present
import com.psato.kdbexemple.R
import com.psato.kdbexemple.infrastructure.bindView
import com.psato.kdbexemple.presentation.base.BaseFragment
import com.psato.twowaybind.TwoWayBindable
import com.psato.twowaybind.bindableText
import org.koin.androidx.viewmodel.ext.android.viewModel

class QueryFragment : BaseFragment(), TwoWayBindable {
    val searchButton: Button by bindView(R.id.search_button)
    val loadinLayout: ViewGroup by bindView(R.id.loading_layout)
    val queryEditText: EditText by bindView(R.id.edit_query)
    val showResponse: RecyclerView by bindView(R.id.response_recycler_view)
    val queryViewModelArc: QueryViewModelArc by viewModel()
    lateinit var adapter: QuerryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_query_mvvm, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryViewModelArc.searchEnabled.bind(searchButton::setEnabled)
        queryViewModelArc.showLoading.bind { loadinLayout.present = it }
        queryViewModelArc.queryValue.twoWayBind(queryEditText.bindableText)
        searchButton.onClick(queryViewModelArc::onQueryClick)
        adapter = QuerryAdapter(queryViewModelArc)
        showResponse.layoutManager = LinearLayoutManager(activity)
        showResponse.adapter = adapter
        queryViewModelArc.registerItemsForUpdate(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        Log.d("SATO", "metric QueryFragment finished")
    }
}
