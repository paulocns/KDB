package com.psato.kdbcore.databind

import android.view.View

fun View.onClick(block: (view: View) -> Unit) {
    this.setOnClickListener {
        block(it)
    }
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

var View.present: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }