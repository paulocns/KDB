package com.psato.kdbexemple.presentation.search

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.psato.extensions.AdapterViewModel
import com.psato.kdbexemple.data.entity.ShowResponse
import com.psato.kdbexemple.infrastructure.SingleLiveEvent
import com.psato.kdbexemple.interactor.usecase.show.SearchShows

class QueryViewModelArc
constructor(private val searchShows: SearchShows) : ViewModel(),
    AdapterViewModel<ShowResponseItem> {
    val queryValue = MutableLiveData<String>()

    val showLoading = MutableLiveData<Boolean>()

    val searchEnabled = MutableLiveData<Boolean>()

    private val showList = arrayListOf<ShowResponseItem>()

    private val queryObserver: Observer<String>

    private val notifyShowListUpdate = SingleLiveEvent<Any>()

    init {
        showLoading.value = false
        searchEnabled.value = false
        queryValue.value = ""
        queryObserver = Observer { query -> searchEnabled.value = !TextUtils.isEmpty(query) }
        queryValue.observeForever(queryObserver)
    }

    override fun onCleared() {
        super.onCleared()
        searchShows.unsubscribe()
        queryValue.removeObserver(queryObserver)
        for (showItem in showList) {
            showItem.onCleared()
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
        showLoading.value = true
        queryValue.value?.let {
            searchShows.query = it
            searchShows.execute {

                onComplete { list: List<ShowResponse> ->
                    showLoading.value = false
                    createItemList(list)
                    notifyShowListUpdate.call()
                }

                onError { throwable ->
                    showLoading.value = false
                }
            }
        }
    }

    private fun createItemList(list: List<ShowResponse>) {
        showList.clear()
        for (response: ShowResponse in list) {
            val showItem = ShowResponseItem(response)
            showList.add(showItem)
        }
    }

}
