package dev.kdblib.twowaybind

import android.app.Application
import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class CompoundButtonTwoWayBindingKtTest{

    private lateinit var checkBox: CompoundButton

    @Captor
    private lateinit var captor: ArgumentCaptor<CompoundButton.OnCheckedChangeListener>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        checkBox = Checkable(
            Robolectric.setupActivity(FragmentActivity::class.java)
        )
    }

    @Test
    fun test_oneWayBind(){
        //arrange
        val binder = checkBox.bindableCheck
        checkBox.isChecked = false
        //act
        binder.oneWayBind.invoke(true)
        //assert
        assertTrue(checkBox.isChecked)
    }

    @Test
    fun test_observeField(){
        //arrange
        val spy = spy(checkBox)
        val binder = spy.bindableCheck
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
        val spy = spy(checkBox)
        val binder = spy.bindableCheck
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
        val spy = spy(checkBox)
        val binder = spy.bindableCheck
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
        val spy = spy(checkBox)
        val binder = spy.bindableCheck
        //act
        binder.removeObserver()
        //assert
        verify(spy).setOnCheckedChangeListener(null)
    }

    private open class Checkable @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
    ): CompoundButton(context, attrs, defStyle)
}