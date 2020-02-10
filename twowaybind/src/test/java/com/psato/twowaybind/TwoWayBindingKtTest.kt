package com.psato.twowaybind

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
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

    private inner class TestClass : TwoWayBindable {
        override val lifeCycleOwner: LifecycleOwner
            get() = lifecycleOwner
    }

    private lateinit var testClass: TestClass

    @Captor
    private lateinit var observerCaptor: ArgumentCaptor<Observer<String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycleOwner = Robolectric.setupActivity(FragmentActivity::class.java)
        binderMock = mock()
        liveDataMock = mock()
        testClass = TestClass()
    }

    @Test
    fun test_bind() {
        //arrange

        //act
        testClass.apply {
            liveDataMock.twoWayBind(binderMock)
        }
        //assert
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), Mockito.any())
        Mockito.verify(binderMock).twoWayBind(liveDataMock, lifecycleOwner)
    }

    @Test
    fun test_observer() {
        //arrange
        testClass.apply {
            liveDataMock.twoWayBind(binderMock)
        }
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), observerCaptor.capture())
        val observe = observerCaptor.value
        var called = false
        val function = { it: String ->
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
        testClass.apply {
            liveDataMock.twoWayBind(binderMock)
        }
        Mockito.verify(liveDataMock).observe(eq(lifecycleOwner), observerCaptor.capture())
        val observe = observerCaptor.value
        //act
        observe.onChanged(null)
        //assert
        Mockito.verify(binderMock, Mockito.never()).oneWayBind
    }
}