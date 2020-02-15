package androidx.lifecycle

import java.io.Closeable


/**
 * A closable ViewModel, that should be created using inside ParentViewModel
 *
 * @see ParentViewModel
 */
abstract class ChildViewModel protected constructor() : ViewModel(), Closeable {

    override fun close() {
        clear()
    }
}