package com.psato.kdbexemple.presentation.home

import android.arch.lifecycle.ViewModel
import android.view.View
import com.psato.kdbexemple.infrastructure.SingleLiveEvent

class HomeFragmentViewModel : ViewModel() {

    val startSearch = SingleLiveEvent<Any>()

    fun onMVVMClicked(view: View) {
        startSearch.call()
    }
}
