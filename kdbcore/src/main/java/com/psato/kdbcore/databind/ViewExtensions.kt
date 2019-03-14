package com.psato.kdbcore.databind

import android.view.View


/**
 * This method set the onClickListener on this view and calls the function received
 * as a parameter when the view is clicked.
 *
 * @param block function that receives a View as a parameter.
 */
fun View.onClick(block: (view: View) -> Unit) {
    this.setOnClickListener {
        block(it)
    }
}

/**
 * This property set the visibility to VISIBLE or to INVISIBLE according to the boolean received.
 */
var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

/**
 * This property set the visibility to VISIBLE or to GONE according to the boolean received.
 */
var View.present: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }