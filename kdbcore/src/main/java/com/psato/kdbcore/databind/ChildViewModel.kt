package com.psato.kdbcore.databind

import android.arch.lifecycle.ViewModel

abstract class ChildViewModel : ViewModel() {
    public override fun onCleared() {
        super.onCleared()
    }
}