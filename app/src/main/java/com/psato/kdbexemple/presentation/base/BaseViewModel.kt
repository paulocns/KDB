package com.psato.kdbexemple.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataUpdateListener
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), LiveDataUpdateListener {

    protected fun <T> LiveData<T>.observe(block: (value: T) -> Unit) {
        addUpdateListener(this, block)
    }
}