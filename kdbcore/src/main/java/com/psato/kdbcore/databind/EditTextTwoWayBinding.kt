package com.psato.kdbcore.databind

import android.arch.lifecycle.MutableLiveData
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference

val EditText.bindableText: TwoWayBinder<String>
    get() = object : TwoWayBinder<String>() {

        private var listener: TextWatcher? = null
        override val oneWayBind: (it: String) -> Unit
            get() = { setText(it) }

        override fun observeField(dataReference: WeakReference<MutableLiveData<String>?>) {
            if (listener != null) {
                removeObserver()
            }
            listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val text = dataReference.get()
                    text?.let {
                        if (it.value != s.toString()) {
                            it.value = s.toString()
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    //nothing to do
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //nothing to do
                }

            }

            addTextChangedListener(listener)
        }

        override fun removeObserver() {
            listener?.let {
                removeTextChangedListener(listener)
            }
        }

    }
