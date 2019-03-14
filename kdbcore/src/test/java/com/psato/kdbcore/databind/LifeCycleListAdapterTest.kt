package com.psato.kdbcore.databind

import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class LifeCycleListAdapterTest{
    private class Item

    @Mock
    private lateinit var viewHolderMock: LifeCycleViewHolder

    private lateinit var lifeCycleAdapter: LifeCycleListAdapter<Item, LifeCycleViewHolder>

    @Mock
    private lateinit var itemCallbackMock: DiffUtil.ItemCallback<Item>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val adapter = object : LifeCycleListAdapter<Item, LifeCycleViewHolder>(itemCallbackMock) {
            override fun onBindViewHolderImpl(holder: LifeCycleViewHolder, position: Int) {
                //nothing to do
            }

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LifeCycleViewHolder {
                return viewHolderMock
            }
        }
        lifeCycleAdapter = Mockito.spy(adapter)
    }

    @Test
    fun test_onBindViewHolder() {
        //arrange
        //act
        lifeCycleAdapter.onBindViewHolder(viewHolderMock, 0)
        //assert
        Mockito.verify(viewHolderMock).notifyBindViewHolder()
        Mockito.verify(lifeCycleAdapter).onBindViewHolder(viewHolderMock, 0)
    }

    @Test
    fun onViewRecycled() {
        //arrange
        //act
        lifeCycleAdapter.onViewRecycled(viewHolderMock)
        //assert
        Mockito.verify(viewHolderMock).notifyUnbindViewHolder()
    }
}