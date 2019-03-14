package com.psato.kdbcore.databind

import android.view.ViewGroup
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LifeCycleAdapterTest {

    private class Item

    @Mock
    private lateinit var viewHolderMock: LifeCycleViewHolder

    private lateinit var lifeCycleAdapter: LifeCycleAdapter<Item, LifeCycleViewHolder>
    @Mock
    private lateinit var viewModelMock: AdapterViewModel<Item>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val adapter = object : LifeCycleAdapter<Item, LifeCycleViewHolder>(viewModelMock) {
            override fun onBindViewHolderImpl(holder: LifeCycleViewHolder, position: Int) {
                //nothing to do
            }

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LifeCycleViewHolder {
                return viewHolderMock
            }
        }
        lifeCycleAdapter = spy(adapter)
    }

    @Test
    fun test_getItemCount() {
        //arrange
        Mockito.`when`(viewModelMock.getItemsCount()).thenReturn(2)
        //act
        val result = lifeCycleAdapter.itemCount
        //assert
        assertEquals(2, result)
        verify(viewModelMock).getItemsCount()
    }

    @Test
    fun test_getItemViewType() {
        //arrange
        Mockito.`when`(viewModelMock.getViewType(anyInt())).thenReturn(2)
        //act
        val result = lifeCycleAdapter.getItemViewType(0)
        //assert
        assertEquals(2, result)
        verify(viewModelMock).getViewType(0)
    }

    @Test
    fun test_onBindViewHolder() {
        //arrange
        //act
        lifeCycleAdapter.onBindViewHolder(viewHolderMock, 0)
        //assert
        verify(viewHolderMock).notifyBindViewHolder()
        verify(lifeCycleAdapter).onBindViewHolder(viewHolderMock, 0)
    }

    @Test
    fun onViewRecycled() {
        //arrange
        //act
        lifeCycleAdapter.onViewRecycled(viewHolderMock)
        //assert
        verify(viewHolderMock).notifyUnbindViewHolder()
    }

}