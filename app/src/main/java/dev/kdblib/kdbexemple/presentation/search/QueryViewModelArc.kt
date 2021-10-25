package dev.kdblib.kdbexemple.presentation.search


import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ParentViewModel
import androidx.lifecycle.viewModelScope
import dev.kdblib.kdbexemple.usecase.show.SearchShows
import dev.kdblib.extensions.AdapterViewModel
import dev.kdblib.extensions.LiveDataFactory
import dev.kdblib.extensions.LiveDataSetter
import dev.kdblib.kdbexemple.infrastructure.SingleLiveEvent
import dev.kdblib.kdbexemple.presentation.base.BaseViewModel

class QueryViewModelArc
constructor(private val searchShows: SearchShows) : BaseViewModel(),
    LiveDataFactory, LiveDataSetter, ParentViewModel,
    AdapterViewModel<ShowResponseItem> {
    val queryValue by liveData("")

    val showLoading by liveData(false)

    val searchEnabled by liveData(false)

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
            searchShows(viewModelScope, it) {

                onComplete { list: List<dev.kdblib.kdbexemple.data.entity.ShowResponse> ->
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

    private fun createItemList(list: List<dev.kdblib.kdbexemple.data.entity.ShowResponse>) {
        showList.clear()
        for (response: dev.kdblib.kdbexemple.data.entity.ShowResponse in list) {
            val showItem = childViewModel { ShowResponseItem(response) }
            showList.add(showItem)
        }
    }

}
