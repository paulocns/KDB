package com.psato.kdbcore.databind

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.support.v4.app.FragmentActivity
import android.widget.CompoundButton
import android.widget.RadioButton
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class RadioButtonTwoWayBindingKtTest{

    private lateinit var radioButton: RadioButton

    @Captor
    private lateinit var captor: ArgumentCaptor<CompoundButton.OnCheckedChangeListener>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        radioButton = RadioButton(Robolectric.setupActivity(FragmentActivity::class.java))
    }

    @Test
    fun test_oneWayBind(){
        //arrange
        val binder = radioButton.bindableRadio
        radioButton.isChecked = false
        //act
        binder.oneWayBind.invoke(true)
        //assert
        assertTrue(radioButton.isChecked)
    }

    @Test
    fun test_observeField(){
        //arrange
        val spy = spy(radioButton)
        val binder = spy.bindableRadio
        val data = mock(MutableLiveData::class.java) as MutableLiveData<Boolean>
        val reference:WeakReference<MutableLiveData<Boolean>?> = WeakReference(data)
        //act
        binder.observeField(reference)
        //assert
        verify(spy).setOnCheckedChangeListener(any())
    }

    @Test
    fun test_observeFieldListenerDataNotNull(){
        //arrange
        val spy = spy(radioButton)
        val binder = spy.bindableRadio
        val data = mock(MutableLiveData::class.java) as MutableLiveData<Boolean>
        val reference:WeakReference<MutableLiveData<Boolean>?> = WeakReference(data)
        binder.observeField(reference)
        verify(spy).setOnCheckedChangeListener(captor.capture())
        val listener = captor.value
        //act
        listener.onCheckedChanged(spy,true)
        //assert
        verify(data).value = true
    }

    @Test
    fun test_observeFieldListenerDataNull(){
        //arrange
        val spy = spy(radioButton)
        val binder = spy.bindableRadio
        val reference:WeakReference<MutableLiveData<Boolean>?> = WeakReference(null)
        binder.observeField(reference)
        verify(spy).setOnCheckedChangeListener(captor.capture())
        val listener = captor.value
        //act
        listener.onCheckedChanged(spy,true)
        //assert
        //just to make sure no crashes happened
    }

    @Test
    fun test_removeObserver(){
        //arrange
        val spy = spy(radioButton)
        val binder = spy.bindableRadio
        //act
        binder.removeObserver()
        //assert
        verify(spy).setOnCheckedChangeListener(null)
    }
}