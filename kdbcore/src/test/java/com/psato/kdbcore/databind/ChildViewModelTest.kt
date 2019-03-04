package com.psato.kdbcore.databind

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ChildViewModelTest {

    private lateinit var childViewModel: ChildViewModel

    private class TestChildViewModel : ChildViewModel()

    @Before
    fun setUp() {
        childViewModel = spy(TestChildViewModel())
    }

    @Test
    fun test_onCleared() {
        //arrange
        //act
        childViewModel.onCleared()
        //assert
        verify(childViewModel).onCleared()
    }

}