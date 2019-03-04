package com.psato.kdbexemple.presentation.search

import android.arch.lifecycle.MutableLiveData
import com.psato.kdbcore.databind.ChildViewModel
import com.psato.kdbexemple.data.entity.ShowResponse

class ShowResponseItem(val showResponse: ShowResponse) : ChildViewModel() {
    val showName = MutableLiveData<String>()

    init {
        showName.value = showResponse.name
    }
}