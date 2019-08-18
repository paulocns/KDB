package com.psato.twowaybind

import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class EditTextTwoWayBindingKtTest {
    private lateinit var editText: EditText

    @Captor
    private lateinit var captor: ArgumentCaptor<TextWatcher>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        editText = EditText(Robolectric.setupActivity(FragmentActivity::class.java))
    }

    @Test
    fun test_oneWayBind() {
        //arrange
        val binder = editText.bindableText
        editText.setText("1")
        //act
        binder.oneWayBind.invoke("2")
        //assert
        assertEquals("2", editText.text.toString())
    }

    @Test
    fun test_oneWayBindEquals() {
        //arrange
        val spy = Mockito.spy(editText)
        val binder = spy.bindableText
        doReturn(SpannableStringBuilder("2")).`when`(spy).text
        //act
        binder.oneWayBind.invoke("2")
        //assert
        verify(spy, never()).setText(ArgumentMatchers.anyString())
    }

    @Test
    fun test_observeFieldListenerNull() {
        //arrange
        val spy = Mockito.spy(editText)
        val binder = spy.bindableText
        val data = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
        val reference: WeakReference<MutableLiveData<String>?> = WeakReference(data)
        //act
        binder.observeField(reference)
        //assert
        Mockito.verify(spy).addTextChangedListener(Mockito.any())
    }

    @Test
    fun test_observeFieldListenerNotNull() {
        //arrange
        val spy = Mockito.spy(editText)
        val binder = spy.bindableText
        val data = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
        val reference: WeakReference<MutableLiveData<String>?> = WeakReference(data)
        binder.observeField(reference)
        //act
        binder.observeField(reference)
        //assert
        Mockito.verify(spy, times(2)).addTextChangedListener(Mockito.any())
        Mockito.verify(spy).removeTextChangedListener(any())
    }

    @Test
    fun test_observeFieldOnTextChangedDataNotNullDifferent() {
        //arrange
        val spy = Mockito.spy(editText)
        val binder = spy.bindableText
        val data = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
        val reference: WeakReference<MutableLiveData<String>?> = WeakReference(data)
        binder.observeField(reference)
        Mockito.verify(spy).addTextChangedListener(captor.capture())
        val listener = captor.value
        //act
        listener.onTextChanged("2",0,0,1)
        //assert
        Mockito.verify(data).value = "2"
    }

    @Test
    fun test_observeFieldAfterTextChangedDataNotNullEquals() {
        //arrange
        val spy = Mockito.spy(editText)
        val binder = spy.bindableText
        val data = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
        val reference: WeakReference<MutableLiveData<String>?> = WeakReference(data)
        binder.observeField(reference)
        Mockito.verify(spy).addTextChangedListener(captor.capture())
        val listener = captor.value
        Mockito.`when`(data.value).thenReturn("2")
        //act
        listener.afterTextChanged(SpannableStringBuilder("2"))
        //assert
        Mockito.verify(data, never()).value = "2"
    }

    @Test
    fun test_observeFieldListenerDataNull() {
        //arrange
        val spy = Mockito.spy(editText)
        val binder = spy.bindableText
        val reference: WeakReference<MutableLiveData<String>?> = WeakReference(null)
        binder.observeField(reference)
        Mockito.verify(spy).addTextChangedListener(captor.capture())
        val listener = captor.value
        //act
        listener.afterTextChanged(SpannableStringBuilder("2"))
        //assert
        //just to make sure no crashes happened
    }

    @Test
    fun test_removeObserver() {
        //arrange
        val spy = Mockito.spy(editText)
        val binder = spy.bindableText
        val data = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
        val reference: WeakReference<MutableLiveData<String>?> = WeakReference(data)
        binder.observeField(reference)
        //act
        binder.removeObserver()
        //assert
        Mockito.verify(spy).addTextChangedListener(captor.capture())
        val listener = captor.value
        Mockito.verify(spy).removeTextChangedListener(listener)
    }
}