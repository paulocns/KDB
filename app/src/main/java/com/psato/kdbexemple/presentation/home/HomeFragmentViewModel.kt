package com.psato.kdbexemple.presentation.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.psato.kdbexemple.infrastructure.SingleLiveEvent

class HomeFragmentViewModel : ViewModel() {

    val startSearch = SingleLiveEvent<Any>()

    fun onMVVMClicked(view: View) {
        startSearch.call()
    }
}
