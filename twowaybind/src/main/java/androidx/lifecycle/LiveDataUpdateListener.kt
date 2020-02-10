package androidx.lifecycle

import java.io.Closeable


/**
 * Interface to be able to listen to updates on a MutableLiveData inside a ViewModel
 * and automatically remove the listener when the ViewModel is cleared
 */
interface LiveDataUpdateListener {

    /**
     * Add an forever listener to a LiveData that will be removed when the
     * on cleared method is called from the ViewModel
     *
     * @param T the type of the LiveData
     * @param data  the LiveData that you want to listen to changes
     * @param block the block of code that will be executed every time that
     * value in the LiveData changes
     */
    fun <T> ViewModel.addUpdateListener(data: LiveData<T>, block: (value: T) -> Unit) {
        val observer = Observer<T> { block(it) }
        data.observeForever(observer)
        setTagIfAbsent(block.hashCode().toString(), LiveDataUpdater(data, observer))
    }

    private class LiveDataUpdater<T>(
        private val liveData: LiveData<T>,
        private val observer: Observer<T>
    ) : Closeable {
        override fun close() {
            liveData.removeObserver(observer)
        }
    }
}