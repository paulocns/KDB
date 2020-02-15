package androidx.lifecycle

/**
 * Interface designed to create a ChildViewModel the child view model will
 * clear when the parent viewmodel finishes
 *
 * @see ChildViewModel
 */
interface ParentViewModel {

    /**
     *  Lazy ChildViewModel creation for the current  ParentViewModel.
     *  the child will be cleared when the parent finishes
     */
    fun <T : ChildViewModel> ViewModel.childViewModel(block: () -> T): T {
        val childViewModel = block()
        setTagIfAbsent(childViewModel.hashCode().toString(), childViewModel)
        return childViewModel
    }

}