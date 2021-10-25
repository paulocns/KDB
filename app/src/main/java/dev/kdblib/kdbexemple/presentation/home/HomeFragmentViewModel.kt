package dev.kdblib.kdbexemple.presentation.home

import android.view.View
import androidx.lifecycle.ViewModel
import dev.kdblib.kdbexemple.infrastructure.SingleLiveEvent

class HomeFragmentViewModel : ViewModel() {

    val startSearch = SingleLiveEvent<Any>()

    fun onMVVMClicked(view: View) {
        startSearch.call()
    }
}
