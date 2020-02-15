package com.psato.kdbexemple.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ChildViewModel
import com.psato.kdbexemple.data.entity.ShowResponse

class ShowResponseItem(showResponse: ShowResponse) : ChildViewModel() {
    val showName = MutableLiveData<String>()

    init {
        showName.value = showResponse.name
    }
}