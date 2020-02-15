package com.psato.kdbexemple.presentation.search


import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ParentViewModel
import androidx.lifecycle.ViewModelCoroutineScoper
import com.psato.extensions.AdapterViewModel
import com.psato.extensions.LiveDataFactory
import com.psato.extensions.LiveDataSetter
import com.psato.kdbexemple.data.entity.ShowResponse
import com.psato.kdbexemple.infrastructure.SingleLiveEvent
import com.psato.kdbexemple.interactor.usecase.show.SearchShows
import com.psato.kdbexemple.presentation.base.BaseViewModel

class QueryViewModelArc
constructor(private val searchShows: SearchShows) : BaseViewModel(),
    LiveDataFactory, LiveDataSetter, ParentViewModel,
    ViewModelCoroutineScoper, AdapterViewModel<ShowResponseItem> {
    val queryValue by liveData("")

    val showLoading by liveData(false)

    val searchEnabled by liveData(false)

    private val scope by viewModelScope()

    private val showList = arrayListOf<ShowResponseItem>()


    private val notifyShowListUpdate = SingleLiveEvent<Any>()

    init {
        queryValue.observe { query ->
            searchEnabled(!TextUtils.isEmpty(query))
        }
    }

    override fun getItemsCount(): Int {
        return showList.size
    }

    override fun getItemAtPosition(pos: Int): ShowResponseItem {
        return showList[pos]
    }

    fun registerItemsForUpdate(owner: LifecycleOwner, observer: Observer<Any>) {
        notifyShowListUpdate.observe(owner, observer)
    }

    fun onQueryClick(view: View) {
        searchShow()
    }

    private fun searchShow() {
        showLoading(true)
        queryValue.value?.let {
            searchShows(scope, it) {

                onComplete { list: List<ShowResponse> ->
                    showLoading(false)
                    createItemList(list)
                    notifyShowListUpdate.call()
                }

                onError { throwable ->
                    showLoading(false)
                }
            }
        }
    }

    private fun createItemList(list: List<ShowResponse>) {
        showList.clear()
        for (response: ShowResponse in list) {
            val showItem = childViewModel { ShowResponseItem(response) }
            showList.add(showItem)
        }
    }

}
