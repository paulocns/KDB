package com.psato.kdbcore.databind

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.view.View
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
        val holder = object : LifeCycleViewHolder(viewMock){

        }
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

}