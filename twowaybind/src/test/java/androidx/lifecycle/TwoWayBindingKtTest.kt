package androidx.lifecycle

import android.app.Application
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.eq
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class TwoWayBindingKtTest {

    private lateinit var liveDataMock: MutableLiveData<String>


    private inner class TestClass : ViewModel(), LiveDataUpdateListener

    private lateinit var testClass: TestClass

    @Captor
    private lateinit var observerCaptor: ArgumentCaptor<Observer<String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        liveDataMock = mock()
        testClass = TestClass()
    }

    @Test
    fun test_addUpdateListener() {
        //arrange
        //act
        testClass.apply {
            addUpdateListener(liveDataMock) {
            }
        }
        //assert
        Mockito.verify(liveDataMock).observeForever(Mockito.any())
    }

    @Test
    fun test_addUpdateListener_value() {
        //arrange
        var called = false
        testClass.apply {
            addUpdateListener(liveDataMock) {
                called = true
            }
        }
        Mockito.verify(liveDataMock).observeForever(observerCaptor.capture())
        val observe = observerCaptor.value
        //act
        observe.onChanged("test")
        //assert
        assertTrue(called)
    }

    @Test
    fun test_removeOnClear() {
        //arrange
        testClass.apply {
            addUpdateListener(liveDataMock) {
            }
        }
        Mockito.verify(liveDataMock).observeForever(observerCaptor.capture())
        val observe = observerCaptor.value
        //act
        testClass.clear()
        //assert
        Mockito.verify(liveDataMock).removeObserver(observe)
    }
}