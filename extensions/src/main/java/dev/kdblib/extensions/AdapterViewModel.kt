package dev.kdblib.extensions

interface AdapterViewModel<T> {

    /**
     * get how many items are in the group to be displayed.
     * @return The new size of the group.
     */
    fun getItemsCount(): Int

    /**
     * Get an item that is in the group at a position.
     * @param pos The position on the group.
     * @return The item.
     */
    fun getItemAtPosition(pos: Int): T

    /**
     * Get a type of the view of an item that is in the group at a position.
     * @param position The position on the group.
     * @return The type of the view.
     */
    fun getViewType(position: Int): Int {
        return 0
    }

}