package dev.kdblib.twowaybind

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class TwoWayBinderTest {

    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var liveDataMock: MutableLiveData<String>

    private lateinit var binder: TwoWayBinder<String>

    private lateinit var lifeCycleMock: Lifecycle

    private var twoWayBinderCalled = false
    private var unbindCalled = false
    private var dataReferenceParameter: WeakReference<MutableLiveData<String>?>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        binder = object : TwoWayBinder<String>() {
            override val oneWayBind: (it: String) -> Unit
                get() = { fail() }

            override fun observeField(dataReference: WeakReference<MutableLiveData<String>?>) {
                twoWayBinderCalled = true
                dataReferenceParameter = dataReference
            }

            override fun removeObserver() {
                unbindCalled = true
            }

        }
        lifecycleOwner = mock(LifecycleOwner::class.java)
        lifeCycleMock = mock(Lifecycle::class.java)
        twoWayBinderCalled = false
        unbindCalled = false
        Mockito.`when`(lifecycleOwner.lifecycle).thenReturn(lifeCycleMock)
        liveDataMock = Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>
    }

    @Test
    fun test_twoWayBinder() {
        //arrange
        //act
        binder.twoWayBind(liveDataMock, lifecycleOwner)
        //assert
        verify(lifecycleOwner).lifecycle
        verify(lifeCycleMock).addObserver(binder)
        assertTrue(twoWayBinderCalled)
        assertEquals(liveDataMock, dataReferenceParameter?.get())
    }

    @Test
    fun test_onDestroy() {
        //arrange
        binder.twoWayBind(liveDataMock, lifecycleOwner)
        //act
        binder.onDestroy()
        //assert
        assertTrue(unbindCalled)
        verify(lifecycleOwner, times(2)).lifecycle
        verify(lifeCycleMock).removeObserver(binder)
    }
}