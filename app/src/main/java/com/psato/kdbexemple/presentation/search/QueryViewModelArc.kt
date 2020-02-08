package com.psato.kdbexemple.presentation.search


import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveDataUpdateListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ParentViewModel
import androidx.lifecycle.ViewModel
import com.psato.extensions.AdapterViewModel
import com.psato.extensions.LiveDataFactory
import com.psato.extensions.LiveDataSetter
import com.psato.kdbexemple.data.entity.ShowResponse
import com.psato.kdbexemple.infrastructure.SingleLiveEvent
import com.psato.kdbexemple.interactor.usecase.show.SearchShows

class QueryViewModelArc
constructor(private val searchShows: SearchShows) : ViewModel(),
    LiveDataFactory, LiveDataSetter, LiveDataUpdateListener, ParentViewModel,
    AdapterViewModel<ShowResponseItem> {
    val queryValue by liveData("")

    val showLoading by liveData(false)

    val searchEnabled by liveData(false)

    private val showList = arrayListOf<ShowResponseItem>()


    private val notifyShowListUpdate = SingleLiveEvent<Any>()

    init {
        addUpdateListener(queryValue) { query ->
            searchEnabled(!TextUtils.isEmpty(query))
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchShows.unsubscribe()
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
            searchShows.query = it
            searchShows.execute {

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
