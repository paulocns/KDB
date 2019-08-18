package com.psato.onewaybind

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentActivity
import android.widget.EditText
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.eq
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OneWayBindingKtTest {
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var liveDataMock: MutableLiveData<String>

    private lateinit var editTextMock: EditText

    @Captor
    private lateinit var observerCaptor: ArgumentCaptor<Observer<String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycleOwner = Robolectric.setupActivity(FragmentActivity::class.java)
        editTextMock = Mockito.mock(EditText::class.java)
        liveDataMock = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
    }

    @Test
    fun test_bind() {
        //arrange

        //act
        lifecycleOwner.bind(liveDataMock, editTextMock::setText)
        //assert
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), Mockito.any())
    }

    @Test
    fun test_observer() {
        //arrange
        lifecycleOwner.bind(liveDataMock) { editTextMock.setText(it) }
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), observerCaptor.capture())
        val observe = observerCaptor.value
        //act
        observe.onChanged("value2")
        //assert
        Mockito.verify(editTextMock).setText("value2")
    }

    @Test
    fun test_observerNull() {
        //arrange
        lifecycleOwner.bind(liveDataMock, editTextMock::setText)
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), observerCaptor.capture())
        val observe = observerCaptor.value
        //act
        observe.onChanged(null)
        //assert
        Mockito.verify(editTextMock, never()).setText("value2".toUpperCase())
    }
}