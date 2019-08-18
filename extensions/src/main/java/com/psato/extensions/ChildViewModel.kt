package com.psato.extensions

import androidx.lifecycle.ViewModel


/**
 * Just makes public the onCleared method.
 */
abstract class ChildViewModel : ViewModel() {
    public override fun onCleared() {
        super.onCleared()
    }
}