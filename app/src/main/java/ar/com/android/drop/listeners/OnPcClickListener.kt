package ar.com.android.drop.listeners

import ar.com.android.drop.domine.Pc

interface OnPcClickListener {

    /**
     * When the user clicks on each row this method will be invoked.
     */
    fun onUpdate(position: Int, model: Pc)

    /**
     * when the user clicks on delete icon this method will be invoked to remove item at position.
     */
    fun onDelete(model: Pc)

}