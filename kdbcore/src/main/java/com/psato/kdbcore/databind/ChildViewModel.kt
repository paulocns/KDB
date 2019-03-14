package com.psato.kdbcore.databind

import android.arch.lifecycle.ViewModel


/**
 * Just makes public the onCleared method.
 */
abstract class ChildViewModel : ViewModel() {
    public override fun onCleared() {
        super.onCleared()
    }
}