package com.psato.extensions

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LifeCycleViewHolderTest{

    lateinit var viewHolder: LifeCycleViewHolder

    @Mock
    lateinit var lifeCycleRegistreMock: LifecycleRegistry

    @Mock
    lateinit var viewMock: View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val holder = testClass()
        viewHolder = spy(holder)
    }

    @Test
    fun test_getLifecycle(){
        //arrange
        //act
        val lifeCycle = viewHolder.lifecycle
        //assert
        assertNotNull(lifeCycle)
    }

    @Test
    fun test_notifyBindViewHolder(){
        //arrange
        doReturn(lifeCycleRegistreMock).`when`(viewHolder).lifecycle
        //act
        viewHolder.notifyBindViewHolder()
        //assert
        verify(lifeCycleRegistreMock).handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun test_notifyUnbindViewHolder(){
        //arrange
        doReturn(lifeCycleRegistreMock).`when`(viewHolder).lifecycle
        //act
        viewHolder.notifyUnbindViewHolder()
        //assert
        verify(lifeCycleRegistreMock).handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }


    private open inner class testClass : LifeCycleViewHolder(viewMock)
}