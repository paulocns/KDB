package androidx.lifecycle

import java.io.Closeable

interface LiveDataUpdateListener {

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