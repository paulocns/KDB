package androidx.lifecycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.io.Closeable


/**
 * Interface designed to create a Coroutine Scope that will be
 * cancelled when the view Model finishes
 */
interface ViewModelCoroutineScoper {
    /**
     *  Lazy CoroutineScope creation for the current viewmodel.
     *  The scope will be canceled when the viewmodel is finished
     */
    fun ViewModel.viewModelScope() = lazy {
        val holder = ViewModelScopeHolder()
        setTagIfAbsent(holder.hashCode().toString(),holder)
        holder.viewModelScope
    }
}

private class ViewModelScopeHolder : Closeable {
    private val viewModelJob = SupervisorJob()
    var viewModelScope = CoroutineScope(viewModelJob)

    override fun close() {
        viewModelJob.cancelChildren()
    }
}
