package dev.kdblib.kdbexemple.presentation.search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kdblib.kdbexemple.R
import dev.kdblib.kdbexemple.infrastructure.bindView
import dev.kdblib.kdbexemple.infrastructure.onClick
import dev.kdblib.kdbexemple.infrastructure.present
import dev.kdblib.kdbexemple.presentation.base.BaseFragment
import dev.kdblib.twowaybind.TwoWayBindable
import dev.kdblib.twowaybind.bindableText
import org.koin.androidx.viewmodel.ext.android.viewModel

class QueryFragment : BaseFragment(), TwoWayBindable {
    private val searchButton: Button by bindView(R.id.search_button)
    private val loadingLayout: ViewGroup by bindView(R.id.loading_layout)
    private val queryEditText: EditText by bindView(R.id.edit_query)
    private val showResponse: RecyclerView by bindView(R.id.response_recycler_view)
    private val queryViewModelArc: QueryViewModelArc by viewModel()
    lateinit var adapter: QuerryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_query_mvvm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryViewModelArc.searchEnabled.bind(searchButton::setEnabled)
        queryViewModelArc.showLoading.bind { loadingLayout.present = it }
        queryViewModelArc.queryValue.twoWayBind(queryEditText.bindableText)
        searchButton.onClick(queryViewModelArc::onQueryClick)
        adapter = QuerryAdapter(queryViewModelArc)
        showResponse.layoutManager = LinearLayoutManager(activity)
        showResponse.adapter = adapter
        queryViewModelArc.registerItemsForUpdate(viewLifecycleOwner,{
            adapter.notifyDataSetChanged()
        })
        Log.d("SATO", "metric QueryFragment finished")
    }
}
