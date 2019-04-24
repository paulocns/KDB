package com.psato.kdbcore.databind

import android.arch.lifecycle.MutableLiveData
import android.widget.CompoundButton
import java.lang.ref.WeakReference


/**
 * @property bindableCheck TwoWayBinder for the check field.
 */
val CompoundButton.bindableCheck: TwoWayBinder<Boolean>
    get() = object : TwoWayBinder<Boolean>() {
        override val oneWayBind: (it: Boolean) -> Unit
            get() = { isChecked = it }

        override fun observeField(dataReference: WeakReference<MutableLiveData<Boolean>?>) {
            setOnCheckedChangeListener { _, isChecked ->
                val data = dataReference.get()
                data?.let {
                    data.value = isChecked
                }
            }
        }

        override fun removeObserver() {
            setOnCheckedChangeListener(null)
        }

    }