package com.psato.extensions

import androidx.lifecycle.ViewModel
import java.io.Closeable


/**
 * Just makes public the onCleared method.
 */
abstract class ChildViewModel : ViewModel, Closeable {
    protected constructor() : super()

    public override fun onCleared() {
        super.onCleared()
    }

    override fun close() {
        super.onCleared()
    }
}