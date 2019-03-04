package com.psato.kdbcore.databind

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.FragmentActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.eq
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class TwoWayBindingKtTest {
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var liveDataMock: MutableLiveData<String>

    private lateinit var binderMock: TwoWayBinder<String>

    @Captor
    private lateinit var observerCaptor: ArgumentCaptor<Observer<String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycleOwner = Robolectric.setupActivity(FragmentActivity::class.java)
        binderMock = Mockito.mock(TwoWayBinder::class.java) as TwoWayBinder<String>
        liveDataMock = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
    }

    @Test
    fun test_bind() {
        //arrange

        //act
        lifecycleOwner.twoWayBind(liveDataMock, binderMock)
        //assert
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), Mockito.any())
        Mockito.verify(binderMock).twoWayBind(liveDataMock, lifecycleOwner)
    }

    @Test
    fun test_observer() {
        //arrange
        lifecycleOwner.twoWayBind(liveDataMock, binderMock)
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), observerCaptor.capture())
        val observe = observerCaptor.value
        var called = false
        val function = {it:String ->
            called = true
            assertEquals("value2", it)
        }
        Mockito.`when`(binderMock.oneWayBind).thenReturn(function)
        //act
        observe.onChanged("value2")
        //assert
        Mockito.verify(binderMock).oneWayBind
        assertTrue(called)
    }

    @Test
    fun test_observerNull() {
        //arrange
        lifecycleOwner.twoWayBind(liveDataMock, binderMock)
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), observerCaptor.capture())
        val observe = observerCaptor.value
        //act
        observe.onChanged(null)
        //assert
        Mockito.verify(binderMock, Mockito.never()).oneWayBind
    }
}