package com.psato.kdbcore.databind

import android.view.View

fun View.onClick(block: (view: View) -> Unit) {
    this.setOnClickListener {
        block(it)
    }
}