package androidx.lifecycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.io.Closeable

interface ViewModelCoroutineScoper {
    fun ViewModel.viewModelScope() = lazy {
        val holder = ViewModelScopeHolder()
        setTagIfAbsent(holder.hashCode().toString(),holder)
        holder.viewModelScope
    }
}

private class ViewModelScopeHolder() : Closeable {
    private val viewModelJob = SupervisorJob()
    var viewModelScope = CoroutineScope(viewModelJob)

    override fun close() {
        viewModelJob.cancelChildren()
    }
}
