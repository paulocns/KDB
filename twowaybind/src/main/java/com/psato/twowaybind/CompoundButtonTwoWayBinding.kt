package com.psato.twowaybind

import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import java.lang.ref.WeakReference


/**
 * @property bindableCheck TwoWayBinder for the CompoundButton field.
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