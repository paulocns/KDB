package androidx.lifecycle

import com.psato.extensions.ChildViewModel

interface ParentViewModel {

    fun<T:ChildViewModel> ViewModel.childViewModel(block: () -> T): T {
        val childViewModel = block()
        setTagIfAbsent(childViewModel.hashCode().toString(), childViewModel)
        return childViewModel
    }

}