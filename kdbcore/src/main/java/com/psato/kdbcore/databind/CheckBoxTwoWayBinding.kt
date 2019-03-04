package com.psato.kdbcore.databind

import android.arch.lifecycle.MutableLiveData
import android.widget.CheckBox
import java.lang.ref.WeakReference


val CheckBox.bindableCheck: TwoWayBinder<Boolean>
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