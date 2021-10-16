package dev.kdblib.kdbexemple.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ChildViewModel

class ShowResponseItem(showResponse: dev.kdblib.kdbexemple.data.entity.ShowResponse) : ChildViewModel() {
    val showName = MutableLiveData<String>()

    init {
        showName.value = showResponse.name
    }
}